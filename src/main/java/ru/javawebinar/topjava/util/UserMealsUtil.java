package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


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
        System.out.println(getFilteredWithExceededLoop(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededJavaStreamAPI(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }


    public static List<UserMealWithExceed>  getFilteredWithExceededLoop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDay = new HashMap<>();
        mealList.stream().forEach((x) -> caloriesDay.merge(x.getDate(), x.getCalories(), Integer::sum));
        List<UserMealWithExceed> result = new ArrayList<>();
        mealList.stream().forEach((userMeal) -> {
            if (TimeUtil.isBetween(userMeal.getTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        caloriesDay.get(userMeal.getDate()) <= caloriesPerDay));
            }
        });
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededJavaStreamAPI(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDay = mealList.stream().collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, (oldValue, newValue) -> oldValue + newValue));
        List<UserMealWithExceed> result = mealList.stream().filter((x) -> (TimeUtil.isBetween(x.getTime(), startTime, endTime))).map(
                temp -> {
                    UserMealWithExceed obj = new UserMealWithExceed(temp.getDateTime(), temp.getDescription(), temp.getCalories(), caloriesDay.get(temp.getDate()) <= caloriesPerDay);
                    return obj;
                }
        ).collect(Collectors.toList());
        return result;
    }
}
