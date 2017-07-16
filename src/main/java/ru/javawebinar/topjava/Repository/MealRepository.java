package ru.javawebinar.topjava.Repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAll();
    boolean delete(int id);
    void add(Meal meal);
    Meal getById(int mealsId);
}
