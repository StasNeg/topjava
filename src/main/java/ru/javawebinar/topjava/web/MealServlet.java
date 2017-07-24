package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.to.Filter;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;


    @Override
    public void destroy() {
        if (appCtx != null) {
            appCtx.close();
        }
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        String buttonValue = request.getParameter("button");
        if (buttonValue != null) {
            if (buttonValue.equals("submit")) {
                Filter filter = new Filter(
                        request.getParameter("startDate") == null ? null : DateTimeUtil.parseDate(request.getParameter("startDate")),
                        request.getParameter("endDate") == null ? null : DateTimeUtil.parseDate(request.getParameter("endDate")),
                        request.getParameter("startTime") == null ? null : DateTimeUtil.parseTime(request.getParameter("startTime")),
                        request.getParameter("endTime") == null ? null : DateTimeUtil.parseTime(request.getParameter("endTime")));
                httpSession.setAttribute("filtersClass", filter);
            } else {
                httpSession.setAttribute("filtersClass", new Filter());
            }
        } else {
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")), AuthorizedUser.getId());
            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.save(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                Filter filter = httpSession.getAttribute("filtersClass") == null ? new Filter() : (Filter) httpSession.getAttribute("filtersClass");
                request.setAttribute("meals", mealRestController.getAll(filter.getFilter()));
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
