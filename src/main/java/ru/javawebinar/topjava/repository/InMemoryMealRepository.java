package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository{
    private AtomicInteger id = new AtomicInteger(100000);

    private final Map<Integer,Meal> meals = new ConcurrentHashMap<>();

    public InMemoryMealRepository() {
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020,Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(id.get(), new Meal(id.getAndIncrement(),LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal create(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(id.getAndIncrement());
        }
        else {
            return update(meal);
        }

        return meals.put(meal.getId(), meal);
    }

    @Override
    public Meal update(Meal meal) {
        Meal oldMeal = get(meal.getId());
        if (meal.getDateTime() != null) {
            oldMeal.setDateTime(meal.getDateTime());
        }
        if (meal.getCalories() != 0) {
            oldMeal.setCalories(meal.getCalories());
        }
        if (meal.getDescription() != null) {
            oldMeal.setDescription(meal.getDescription());
        }

        return oldMeal;
    }

    @Override
    public boolean delete(int mealId) {
        return meals.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId) {
        return meals.get(mealId);
    }




}
