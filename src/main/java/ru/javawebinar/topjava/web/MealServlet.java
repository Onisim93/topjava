package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repository = new InMemoryMealRepository();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String forwardPage = "meals.jsp";

        if (action != null) {
            String id = req.getParameter("id");
            switch (action) {
                case "create", "update" -> {
                    Meal newMeal = new Meal();
                    if (id != null) {
                        newMeal = repository.get(Integer.parseInt(id));
                    }
                    req.setAttribute("meal", newMeal);
                    req.getRequestDispatcher("editMeal.jsp").forward(req,resp);
                }
                case "delete" -> {
                    repository.delete(Integer.parseInt(id));
                    log.debug("Delete meal with id = {}", id);
                    resp.sendRedirect("meals");
                }
            }
        }
        else {
            log.debug("forward to meals");
            req.setAttribute("mealsList", MealsUtil.filteredByCycles(repository.getAll(), null, null, MealsUtil.CALORIES_PER_DAY));
            req.getRequestDispatcher(forwardPage).forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        Meal meal = new Meal();

        meal.setDescription(description);
        meal.setDateTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        meal.setCalories(Integer.parseInt(calories));

        if (id != null && !id.isEmpty()) {
            meal.setId(Integer.parseInt(id));
        }


        Meal editMeal = repository.create(meal);

        log.info("Create/Update {}", editMeal);

        resp.sendRedirect("meals");
    }
}
