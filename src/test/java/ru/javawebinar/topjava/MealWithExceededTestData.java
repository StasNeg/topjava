package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;

import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealWithExceededTestData {

    public static final BeanMatcher<MealWithExceed> MATCHERWITHEXSEED = BeanMatcher.of(MealWithExceed.class);

    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final MealWithExceed MEALEXSEED1 = new MealWithExceed(MEAL1_ID, of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, false);
    public static final MealWithExceed MEALEXSEED2 = new MealWithExceed(MEAL1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,false);
    public static final MealWithExceed MEALEXSEED3 = new MealWithExceed(MEAL1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,false);
    public static final MealWithExceed MEALEXSEED4 = new MealWithExceed(MEAL1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500,true);
    public static final MealWithExceed MEALEXSEED5 = new MealWithExceed(MEAL1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "Обед", 1000,true);
    public static final MealWithExceed MEALEXSEED6 = new MealWithExceed(MEAL1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,true);





}
