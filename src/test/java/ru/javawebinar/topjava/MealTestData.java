package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static int SEQ = START_SEQ + 3;

    public static Meal meal1 = new Meal(SEQ, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак", 500);
    public static Meal meal2 = new Meal(SEQ+1, LocalDateTime.parse("2020-01-30T13:00:00"), "Обед", 1000);
    public static Meal meal3 = new Meal(SEQ+2, LocalDateTime.parse("2020-01-30T20:00:00"), "Ужин", 500);
    public static Meal meal4 = new Meal(SEQ+3, LocalDateTime.parse("2020-01-31T00:00:01"), "Еда на граничное значение", 100);
    public static Meal meal5 = new Meal(SEQ+4, LocalDateTime.parse("2020-01-31T10:00:00"), "Завтрак", 1000);
    public static Meal meal6 = new Meal(SEQ+5, LocalDateTime.parse("2020-01-31T13:00:00"), "Обед", 500);
    public static Meal meal7 = new Meal(SEQ+6, LocalDateTime.parse("2020-01-31T20:00:00"), "Ужин", 410);
    public static Meal meal8 = new Meal(SEQ+7, LocalDateTime.parse("2020-01-31T17:00:00"), "Ужин", 1000);

    public static List<Meal> allMeals = new ArrayList<>(){{
        add(meal7);
        add(meal6);
        add(meal5);
        add(meal4);
        add(meal3);
        add(meal2);
        add(meal1);
    }};

    public static List<Meal> getMealsBetweenHalfOpen() {
        return Arrays.asList(meal1,meal2,meal3);
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.parse("2022-07-12T15:30:00"), "Обед", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.parse("2022-07-12T14:00:00"));
        updated.setDescription("Новый обед");
        updated.setCalories(444);
        return updated;
    }

}
