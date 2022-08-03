package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/rest/meals")
public class MealRestController extends AbstractMealController {

    private static final String DATE_FORMATTER = "yyyy-MM-dd";
    private static final String TIME_FORMATTER = "HH:mm:ss";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/by-date")
    public List<MealTo> getBetweenHalfOpen(@RequestParam @Nullable @DateTimeFormat(pattern = DATE_FORMATTER) LocalDate startDate,
                                           @RequestParam @Nullable @DateTimeFormat(pattern = DATE_FORMATTER) LocalDate endDate,
                                           @RequestParam @Nullable @DateTimeFormat(pattern = TIME_FORMATTER)LocalTime startTime,
                                           @RequestParam @Nullable @DateTimeFormat(pattern = TIME_FORMATTER) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Meal> createRest(@RequestBody Meal meal) {
        Meal created = create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/meals/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }




}