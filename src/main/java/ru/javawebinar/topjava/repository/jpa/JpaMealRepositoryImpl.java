package ru.javawebinar.topjava.repository.jpa;


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
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;


    @Transactional(readOnly = false)
    @Override
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Meal mealFind = em.find(Meal.class, meal.getId());
            return mealFind.getUser().getId() == userId ? em.merge(meal) : null;
//            System.out.println(mealFind.getUser().getId());
//
//            int result = em.createNamedQuery(Meal.UPDATE)
//                    .setParameter(1, meal.getDateTime())
//                    .setParameter(2, meal.getDescription())
//                    .setParameter(3, meal.getCalories())
//                    .setParameter(4, meal.getId())
//                    .setParameter(5, userId).executeUpdate();
//            return result == 0 ? null : meal;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id).setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal mealFind = em.find(Meal.class, id);
        return mealFind.getUser().getId() == userId ? mealFind : null;

//        List<Meal> meals = em.createNamedQuery(Meal.BY_ID, Meal.class)
//                .setParameter(1, id).setParameter(2, userId)
//                .getResultList();
//        return DataAccessUtils.singleResult(meals);
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