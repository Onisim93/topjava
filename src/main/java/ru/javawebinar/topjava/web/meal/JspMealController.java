package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController{

    @GetMapping("/filter")
    public String getAllWithFilter(HttpServletRequest request, Model model) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        List<MealTo> meals = getBetween(
                startDate.isEmpty() ? null : LocalDate.parse(startDate),
                startTime.isEmpty() ? null : LocalTime.parse(startTime),
                endDate.isEmpty() ? null : LocalDate.parse(endDate),
                endTime.isEmpty() ? null : LocalTime.parse(endTime));
        model.addAttribute("meals", meals);

        return "meals";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable int id) {
        model.addAttribute("meal", get(id));

        return "mealForm";
    }

    @GetMapping("/create")
    public String save(Model model) {
        Meal meal = new Meal();
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping()
    public String getAll(Model model, HttpServletRequest request) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/{id}/delete")
    public String deleteMeal(@PathVariable int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @PostMapping()
    public String saveMeal(HttpServletRequest request) {
        Meal meal = new Meal();
        String id = request.getParameter("id");
        if (!id.isEmpty()) {
            meal.setId(Integer.valueOf(id));
        }
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        if (meal.isNew()) {
            super.create(meal);
        }
        else {
            super.update(meal, meal.id());
        }
        return "redirect:/meals";
    }
}
