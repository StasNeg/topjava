package ru.javawebinar.topjava.Repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Stanislav on 15.07.2017.
 */
public interface MealRepository {
    List<Meal> getAll();
    boolean delete(int id);
    void add(Meal meal);
    Meal getById(int mealsId);
}
