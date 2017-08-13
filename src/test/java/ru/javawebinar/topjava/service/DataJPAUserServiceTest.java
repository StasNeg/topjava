package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.MATCHER;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJPAUserServiceTest extends AbstractUserServiceTest{
    @Test
    public void testAllMealsByUserId() throws Exception {
        MATCHER.assertCollectionEquals(MEALS, service.getMealsList(USER_ID).getMeals());
    }

}