package ru.javawebinar.topjava.Repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Stanislav on 14.07.2017.
 */
public class MealsRepository {
    private static AtomicInteger id = new AtomicInteger(0);
    private static CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList();
    static {
        meals.add(new Meal(id.getAndAdd(1),LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак",500));
        meals.add(new Meal(id.getAndAdd(1),LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед",1000));
        meals.add(new Meal(id.getAndAdd(1),LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин",500));
        meals.add ( new Meal(id.getAndAdd(1),LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак",1000));
        meals.add (new Meal(id.getAndAdd(1),LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед",500));
        meals.add(new Meal(id.getAndAdd(1),LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин",510));
    }

    public MealsRepository() {
    }

    public List<Meal> getMeals() {
        return meals;
    }


    public static boolean delete(int mealsId) {
        return meals.removeIf(meals -> meals.getId()==mealsId);
    }

    public static Meal getMealsById(int mealsId) {
        return meals.stream().filter(meal -> meal.getId()==mealsId).findFirst().get();
    }
}
