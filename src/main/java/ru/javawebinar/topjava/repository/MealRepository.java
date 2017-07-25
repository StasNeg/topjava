package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int idUser);

    boolean delete(int id, int idUser);

    Meal get(int id, int idUser);

    List<Meal> getAll(int idUser);

    List<Meal> getAll(int idUser, LocalDate start, LocalDate end);
}
