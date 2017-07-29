package ru.javawebinar.topjava.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;


import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceImplTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;
    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher();

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        MealTestData.init();
    }


    @Test
    public void saveMealsUser() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Breakfest", 890);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MealTestData.mealsUser.add(newMeal);
        MATCHER.assertCollectionEquals(MealTestData.mealsUser.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), service.getAll(USER_ID));
    }
    @Test
    public void saveMealsAdmin() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Breakfest", 890);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MealTestData.mealsAdmin.add(newMeal);
        MATCHER.assertCollectionEquals(MealTestData.mealsAdmin.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), service.getAll(ADMIN_ID));
    }
    @Test
    public void getMealsUser() throws Exception {
       MATCHER.assertEquals(MealTestData.mealsUser.get(0),service.get(START_SEQ,USER_ID));
    }
    @Test(expected = NotFoundException.class)
    public void getMealsUserToAdmin() throws Exception {
        service.get(START_SEQ,ADMIN_ID);
    }
    @Test(expected = NotFoundException.class)
    public void saveMealsUserToAdmin() throws Exception {
        service.update(service.get(START_SEQ, USER_ID), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteMealUsersAdmin() throws Exception {
        service.delete(START_SEQ,ADMIN_ID);

    }
    @Test
    public void deleteMealAdminAdmin() throws Exception {
        service.delete(START_SEQ+6,ADMIN_ID);
        MealTestData.mealsAdmin.remove(0);
        MATCHER.assertCollectionEquals(MealTestData.mealsAdmin.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), service.getAll(ADMIN_ID));
    }

    @Test
    public void deleteMealUserUser() throws Exception {
        service.delete(START_SEQ,USER_ID);
        MealTestData.mealsUser.remove(0);
        MATCHER.assertCollectionEquals(MealTestData.mealsUser.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), service.getAll(USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {

    }

    @Test
    public void getAllUsersUsers() throws Exception {
        MATCHER.assertCollectionEquals(MealTestData.mealsUser.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), service.getAll(USER_ID));
    }



}