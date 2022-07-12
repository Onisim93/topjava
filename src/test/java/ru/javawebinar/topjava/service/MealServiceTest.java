package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealRestController controller;

    @Test
    public void get() {
        Meal actual = controller.get(MealTestData.SEQ);
        assertEquals(MealTestData.meal1, actual);
    }

    @Test
    public void getNotOwnMeal() {
        assertThrows(NotFoundException.class, () -> controller.get(MealTestData.meal8.getId()));
    }

    @Test
    public void delete() {
        controller.delete(MealTestData.SEQ);
        assertThrows(NotFoundException.class, () -> controller.get(MealTestData.SEQ));
    }

    @Test
    public void deleteNotOwnMeal() {
        assertThrows(NotFoundException.class, ()->controller.delete(MealTestData.meal8.getId()));
    }

    @Test
    public void getBetweenInclusive() {
        List<MealTo> actual = controller.getBetween(null, null, LocalDate.parse("2020-01-30"), LocalTime.parse("23:00:00"));
        List<MealTo> expected = MealsUtil.getTos(MealTestData.getMealsBetweenHalfOpen(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getAll() {
        List<MealTo> actual = controller.getAll();
        List<MealTo> expected = MealsUtil.getTos(MealTestData.allMeals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void update() {
        Meal expected = MealTestData.getUpdated();
        controller.update(expected, expected.getId());
        Meal actual = controller.get(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateNotOwnMeal() {
        Meal updated = new Meal(MealTestData.meal8);
        updated.setCalories(300);
        updated.setDescription("Новое значение");
        assertThrows(NotFoundException.class, ()->controller.update(updated, updated.getId()));
    }

    @Test
    public void create() {
        Meal actual = controller.create(MealTestData.getNew());
        Integer newId = actual.getId();
        Meal expected = MealTestData.getNew();
        expected.setId(newId);
        assertEquals(expected, actual);
    }

    @Test
    public void createWithDuplicateDateTime() {
        Meal meal = new Meal(MealTestData.meal1);
        meal.setId(null);
        assertThrows(DataAccessException.class, ()->controller.create(meal));
    }
}