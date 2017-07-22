package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.Filter;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;


@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        return getAll(new Filter());
    }

    public List<MealWithExceed> getAll(Filter filter) {
        log.info("getAll");
        LocalDate startDate = filter.getStartDate() == null ? LocalDate.MIN : filter.getStartDate();
        LocalDate endDate = filter.getEndDate() == null ? LocalDate.MAX : filter.getEndDate();
        LocalTime startTime = filter.getStartTime() == null ? LocalTime.MIN : filter.getStartTime();
        LocalTime endTime = filter.getEndTime() == null ? LocalTime.MAX : filter.getEndTime();
        return MealsUtil.getFilteredWithExceeded(service.getAll(startDate, endDate), startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal save(Meal meal) {
        log.info("create {}", meal);
        return service.save(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        checkIdConsistent(meal, id);
        service.update(meal);
    }

}