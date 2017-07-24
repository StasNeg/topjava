package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int idUser) {
        if (meal.isNew())
            return repository.save(meal);
        else if (meal.getUserId() == idUser) {
            return repository.save(meal);
        }
        return null;
    }

    @Override
    public void delete(int id, int idUser) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, idUser), id);
    }

    @Override
    public Meal get(int id, int idUser) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, idUser), id);
    }


    @Override
    public List<Meal> getAll(int idUser) {
        return repository.getAll(idUser);
    }

    @Override
    public List<Meal> getAllFiltered(int idUser, LocalDate start, LocalDate end) {
        return repository.getAll(idUser, start, end);
    }
}