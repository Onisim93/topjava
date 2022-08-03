package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/meals/";

    @Autowired
    MealService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void getBetweenHalfOpen() throws Exception {
        String withAllParameters = REST_URL + "by-date?startDate=2020-01-30&startTime=00:00:01&endDate=2020-01-30&endTime=23:00:00";
        String onlyDate = REST_URL + "by-date?startDate=2020-01-30&endDate=2020-01-30";
        perform(MockMvcRequestBuilders.get(onlyDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(List.of(meal3,meal2,meal1), MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + meal1.id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void createRest() throws Exception {
        Meal m = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(m)))
                .andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(result);
        int newId = created.getId();
        m.setId(newId);
        MEAL_MATCHER.assertMatch(m, created);
        MEAL_MATCHER.assertMatch(m, service.get(newId, SecurityUtil.authUserId()));

    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + meal1.id()))
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NotFoundException.class, ()->service.get(meal1.id(), SecurityUtil.authUserId()));
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + meal1.id())
                .content(JsonUtil.writeValue(updated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Meal actual = service.get(updated.id(), SecurityUtil.authUserId());

        MEAL_MATCHER.assertMatch(actual, updated);
    }
}