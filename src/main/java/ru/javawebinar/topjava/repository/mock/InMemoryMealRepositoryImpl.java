package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);


    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int idUser) {
        log.info("delete", id);
        Meal temp = repository.remove(id);
        if (temp != null && temp.getUserId() == idUser)
            return true;
        save(temp);
        return false;
    }

    @Override
    public Meal get(int id, int idUser) {
        log.info("get", id);
        Meal temp = repository.get(id);
        return temp.getUserId() == idUser ? temp : null;
    }

    @Override
    public List<Meal> getAll(int idUser) {
        return getAll(idUser, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public List<Meal> getAll(int idUser, LocalDate start, LocalDate end) {
        return repository.values().stream().filter(meal -> meal.getUserId() == idUser && DateTimeUtil.isBetween(meal.getDate(), start, end)).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());

    }
}

