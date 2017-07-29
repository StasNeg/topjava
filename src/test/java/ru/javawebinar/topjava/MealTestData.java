package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {
    public static List<Meal> mealsUser;
    public static List<Meal> mealsAdmin;
    public static void init(){
        mealsUser = Arrays.asList(
                new Meal(START_SEQ, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(START_SEQ + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
                new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
                new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        ).stream().collect(Collectors.toList());
        mealsAdmin = Arrays.asList(
                new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510),
                new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)).
                stream().collect(Collectors.toList());
    }

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>();

}
