package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.Repository.MealRepository;
import ru.javawebinar.topjava.Repository.MealRepositoryImpl.MealRepositoryImplConcurrencyArrayList;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Stanislav on 14.07.2017.
 */
public class MealsServlet extends HttpServlet {
    private static String INSERT_OR_EDIT = "/edit.jsp";
    private static String LIST_MEALS = "/users.jsp";
    private MealRepository mealsRepository = new MealRepositoryImplConcurrencyArrayList();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
         request.setCharacterEncoding("UTF-8");
        int id;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        }catch (NumberFormatException e){
            id = -1;
        }
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        int calories = Integer.parseInt(request.getParameter("calories"));
        mealsRepository.add(new Meal(id, dateTime, description, calories));
        request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(new MealRepositoryImplConcurrencyArrayList().getAll(), 2000));
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {
            int mealsId = Integer.parseInt(request.getParameter("mealsId"));
            mealsRepository.delete(mealsId);
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(mealsRepository.getAll(),2000));

        }else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealsId = Integer.parseInt(request.getParameter("mealsId"));
            request.setAttribute("meals", mealsRepository.getById(mealsId));
        } else if (action.equalsIgnoreCase("listMeals")){
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(mealsRepository.getAll(),2000));
        } else {
            forward = INSERT_OR_EDIT;
        }
            request.getRequestDispatcher(forward).forward(request, response);
    }
}
