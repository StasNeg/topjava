package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.Filter;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;



@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        return getAll(new Filter().getFilter());
    }

    public List<MealWithExceed> getAll(Filter filter) {
        log.info("getAll");
        return MealsUtil.getFilteredWithExceeded(
                service.getAllFiltered(AuthorizedUser.getId(),filter.getStartDate(), filter.getEndDate()),
                filter.getStartTime(), filter.getEndTime(), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.getId());
    }

    public Meal save(Meal meal) {
        log.info("create {}", meal);
        return service.save(meal, AuthorizedUser.getId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id,AuthorizedUser.getId());
    }

}