package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    Meal save(Meal Meal, int idUser);

    void delete(int id, int idUser) throws NotFoundException;

    Meal get(int id, int idUser) throws NotFoundException;

    List<Meal> getAll(int idUser);

    List<Meal> getAllFiltered(int idUser, LocalDate start, LocalDate end);
    
}