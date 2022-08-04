package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUiController extends AbstractMealController{

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    public void create(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                       @RequestParam String description,
                       @RequestParam Integer calories) {
        super.create(new Meal(dateTime, description, calories));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                       @RequestParam String description,
                       @RequestParam Integer calories) {
        super.update(new Meal(id,dateTime,description,calories), id);
    }


}
