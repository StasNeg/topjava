package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

/**
 * Created by Stanislav on 19.08.2017.
 */
@Controller
@RequestMapping(value = "/meals")
public class MealController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    @RequestMapping(method = RequestMethod.GET)
    public String users(Model model) {
        int userId = AuthorizedUser.id();
        log.info("getAll for userId={}", userId);
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id, Model model) {
        log.info("delete meal {} for userId={}", id, AuthorizedUser.id());
        service.delete(id,AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String get(@RequestParam("id") Integer id, Model model) {
        log.info("update meal {} for userId={}", id, AuthorizedUser.id());
        service.get(id,AuthorizedUser.id());
        model.addAttribute("meal", service.get(id,AuthorizedUser.id()));
        return "mealForm";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@ModelAttribute("id") Integer id,
                       @ModelAttribute("dateTime") String dateTime,
                       @ModelAttribute("description") String descripton,
                       @ModelAttribute("calories") Integer calories, HttpServletResponse response){
            Meal meal = new Meal(LocalDateTime.parse(dateTime),descripton,calories);
            if (id == null) {
                log.info("create meal for userId={}", AuthorizedUser.id());
                service.create(meal,AuthorizedUser.id());
            } else {
                log.info("update meal {} for userId={}", id, AuthorizedUser.id());
                assureIdConsistent(meal, id);
                service.update(meal, AuthorizedUser.id());
            }

        try {
            response.sendRedirect("/meals");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
