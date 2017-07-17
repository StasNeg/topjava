package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Repository.MealRepository;
import ru.javawebinar.topjava.Repository.MealRepositoryImpl.MealRepositoryConcurrencyHashMapImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;


public class MealsServlet extends HttpServlet {

    private static String INSERT_OR_EDIT = "/edit.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private MealRepository mealsRepository = new MealRepositoryConcurrencyHashMapImpl();
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final Logger log = getLogger(MealsServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("In MealServlets DoPost" );
        request.setCharacterEncoding("UTF-8");
        int id;
        int calories;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        }catch (NumberFormatException e){
            id = -1;
        }
        try {
            calories = Integer.parseInt(request.getParameter("calories"));
        } catch (NumberFormatException e) {
            calories = 0;
        }
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), dateTimeFormatter);
        mealsRepository.add(new Meal(id, dateTime, description, calories));
        request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(mealsRepository.getAll(), 2000));
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(mealsRepository.getAll(), 2000));
            forward = LIST_MEALS;
        } else if (action.equalsIgnoreCase("delete")) {
            int mealsId = Integer.parseInt(request.getParameter("mealsId"));
            if(mealsRepository.delete(mealsId)) {
                request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(mealsRepository.getAll(), 2000));
            }
            log.debug("In MealServlets DoGet action = delete, id = " + mealsId);
            response.sendRedirect("meals");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealsId = Integer.parseInt(request.getParameter("mealsId"));
            request.setAttribute("meal", mealsRepository.getById(mealsId));
        } else {
            forward = INSERT_OR_EDIT;
        }
        log.debug("In MealServlets DoGet action = " + forward );
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
