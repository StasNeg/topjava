package ru.javawebinar.topjava.util;

import jdk.nashorn.internal.objects.annotations.Function;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededJavaAPI(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }
    @Function
    private static LocalDate toLocalDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }

    private static LocalTime toLocalTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> caloriesDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            if (caloriesDay.containsKey(toLocalDate(userMeal.getDateTime()))) {
                caloriesDay.put(toLocalDate(userMeal.getDateTime()), caloriesDay.get(toLocalDate(userMeal.getDateTime())) + userMeal.getCalories());
            } else {
                caloriesDay.put(toLocalDate(userMeal.getDateTime()), userMeal.getCalories());
            }
        }
        for (UserMeal userMeal : mealList) {
            if (!TimeUtil.isBetween(toLocalTime(userMeal.getDateTime()), startTime, endTime)) {
                continue;
            }
            result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                    caloriesDay.get(toLocalDate(userMeal.getDateTime())) <= caloriesPerDay));
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededJavaAPI(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> result = new ArrayList<>();
        Map <LocalDate, Integer>caloriesDay = mealList.stream().collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, (oldValue, newValue) -> oldValue + newValue));
        for (UserMeal userMeal : mealList) {
            if (!TimeUtil.isBetween(toLocalTime(userMeal.getDateTime()), startTime, endTime)) {
                continue;
            }
            result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                    caloriesDay.get(toLocalDate(userMeal.getDateTime())) <= caloriesPerDay));
        }
        return result;
    }
}
