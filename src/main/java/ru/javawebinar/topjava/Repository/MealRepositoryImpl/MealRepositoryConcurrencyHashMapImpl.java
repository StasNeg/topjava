package ru.javawebinar.topjava.Repository.MealRepositoryImpl;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Repository.MealRepository;
import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRepositoryConcurrencyHashMapImpl implements MealRepository {
    private AtomicInteger id = new AtomicInteger(0);
    private Map<Integer, Meal> meals;
    private static final Logger log = getLogger(MealRepositoryConcurrencyHashMapImpl.class);
    public MealRepositoryConcurrencyHashMapImpl() {
        init();
    }

    private void init() {
            log.debug("Init meals");
            this.meals = new ConcurrentHashMap();
            this.id = new AtomicInteger(0);
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public List<Meal> getAll() {
        log.debug("get All meals");
        return new ArrayList<>(meals.values());
    }


    public boolean delete(int mealsId) {
        log.debug("delete meal id=" + mealsId);
        return meals.remove(mealsId) != null;
    }

    public Meal getById(int mealsId) {
        log.debug("Get meal id ="+mealsId);
        return meals.get(mealsId);
    }

    public void add(Meal meal) {
        String debugLog = "";
        if (meal.getId() < 0) {
            meals.put(id.incrementAndGet(), new Meal(id.intValue(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
            debugLog = "Create new meal id=" + id.intValue();
        } else {
            meals.put(meal.getId(), meal);
            debugLog = "Add meal id=" + id.intValue();
        }
        log.debug(debugLog);
    }
}
