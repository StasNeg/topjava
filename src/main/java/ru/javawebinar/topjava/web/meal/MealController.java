package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;


@Controller
@RequestMapping(value = "meals")
public class MealController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
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
        service.get(id, AuthorizedUser.id());
        model.addAttribute("meal", service.get(id, AuthorizedUser.id()));
        return "mealForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.info("create meal for userId={}", AuthorizedUser.id());
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("id") String id,
                       @ModelAttribute("dateTime") String dateTime,
                       @ModelAttribute("description") String descripton,
                       @ModelAttribute("calories") Integer calories) throws ServletException, IOException {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), descripton, calories);
        if (id.equals("")) {
            log.info("create meal for userId={}", AuthorizedUser.id());
            service.create(meal, AuthorizedUser.id());
        } else {
            log.info("update meal {} for userId={}", id, AuthorizedUser.id());
            assureIdConsistent(meal, Integer.parseInt(id));
            service.update(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    public String filter(@ModelAttribute("startDate") String startDate,
                         @ModelAttribute("endDate") String endDate,
                         @ModelAttribute("startTime") String startTime,
                         @ModelAttribute("endTime") String endTime,
                         Model model) throws ServletException, IOException {
        model.addAttribute("meals", getBetween(parseLocalDate(startDate),
                parseLocalTime(startTime),
                parseLocalDate(endDate),
                parseLocalTime(endTime)));
        return "meals";
    }

    private List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for userId={}", startDate, endDate, startTime, endTime, userId);
        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }
}
