package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;


import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;


@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
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