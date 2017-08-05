package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            int result = em.createNamedQuery(Meal.UPDATE)
                    .setParameter(1, meal.getDateTime())
                    .setParameter(2, meal.getDescription())
                    .setParameter(3, meal.getCalories())
                    .setParameter(4, meal.getId())
                    .setParameter(5, userId).executeUpdate();
            return result == 0 ? null : meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id).setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.BY_ID, Meal.class)
                .setParameter(1, id).setParameter(2, userId)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_GET_BETWEEN, Meal.class)
                .setParameter(1, userId).setParameter(2, startDate).setParameter(3, endDate)
                .getResultList();
    }
}