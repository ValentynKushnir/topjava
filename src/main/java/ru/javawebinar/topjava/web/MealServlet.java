package ru.javawebinar.topjava.web;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.javawebinar.topjava.model.MealDto;
import ru.javawebinar.topjava.service.MealSrv;
import ru.javawebinar.topjava.service.MealSrvImpl;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealSrv mealSrv;

    @Override
    public void init() throws ServletException {
        super.init();
        mealSrv = new MealSrvImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "get";
        }
        switch (action) {
            case "get":
                LOG.debug("forward to meals");
                request.setAttribute("meals", mealSrv.getFilteredWithExcess());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", mealSrv.getMealById(id));
                request.setAttribute("newMeal", false);
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            case "new":
                MealDto dto = new MealDto(0, LocalDateTime.now(), "", 0);
                request.setAttribute("meal", dto);
                request.setAttribute("newMeal", true);
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        MealDto dto;
        switch (action) {
            case "post":
                dto = extractMealAttributes(req);
                mealSrv.createMeal(dto.getDescription(), dto.getDateTime(), dto.getCalories());
                break;
            case "put":
                LOG.debug("updating a meal");
                dto = extractMealAttributes(req);
                mealSrv.updateMeal(dto.getId(), dto.getDescription(), dto.getDateTime(), dto.getCalories());
                break;
            case "delete":
                LOG.debug("deleting a meal");
                int id = Integer.parseInt(req.getParameter("id"));
                mealSrv.removeMealById(id);
                break;
            case "new":
                LOG.debug("creating new meal");
                MealDto newMeal = extractMealAttributes(req);
                mealSrv.createMeal(newMeal.getDescription(), newMeal.getDateTime(), newMeal.getCalories());
        }
        resp.sendRedirect("meals");
    }

    private MealDto extractMealAttributes(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        return new MealDto(id, dateTime, description, calories);
    }
}
