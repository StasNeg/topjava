package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

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
    public boolean delete(int id) {
        log.info("delete", id);
        return repository.get(id).getUserId() == AuthorizedUser.id() ? (repository.remove(id) != null) : null;
    }

    @Override
    public Meal get(int id) {
        log.info("get", id);
        return repository.get(id).getUserId() == AuthorizedUser.id() ? repository.get(id) : null;
    }

    @Override
    public List<Meal> getAll() {
        return repository.values().stream().filter(meal -> meal.getUserId() == AuthorizedUser.id()).sorted(new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        }).collect(Collectors.toList());
    }
}

