package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAll();
    Meal get(int mealId);
    Meal create(Meal meal);
    boolean delete(int mealId);
    Meal update(Meal meal);
}
