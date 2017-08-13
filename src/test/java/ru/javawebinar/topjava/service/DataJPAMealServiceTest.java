package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJPAMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void testAllMealsByUserId() throws Exception {
        MATCHER.assertEquals(USER, service.getUserByMealId(MEAL1_ID).getUser());
    }
}