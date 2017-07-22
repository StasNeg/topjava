package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    Meal save(Meal Meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    void update(Meal Meal);

    List<Meal> getAll();
    List<Meal> getAll(LocalDate start, LocalDate end);
    
}