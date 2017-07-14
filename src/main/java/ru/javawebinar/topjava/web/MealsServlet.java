package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.Repository.MealsRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Stanislav on 14.07.2017.
 */
public class MealsServlet extends HttpServlet {
    private static String INSERT_OR_EDIT = "/edit.jsp";
    private static String LIST_MEALS = "/users.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {
            int mealsId = Integer.parseInt(request.getParameter("mealsId"));
            MealsRepository.delete(mealsId);
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(new MealsRepository().getMeals(),2000));

        }else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealsId = Integer.parseInt(request.getParameter("mealsId"));
            request.setAttribute("meals", MealsRepository.getMealsById(mealsId));
        } else if (action.equalsIgnoreCase("listMeals")){
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.getAllMealsWithExceeded(new MealsRepository().getMeals(),2000));
        } else {
            forward = INSERT_OR_EDIT;
        }
            request.getRequestDispatcher(forward).forward(request, response);
    }
}
