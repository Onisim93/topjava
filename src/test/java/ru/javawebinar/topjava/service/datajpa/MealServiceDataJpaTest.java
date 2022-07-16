package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
@ActiveProfiles("datajpa")
public class MealServiceDataJpaTest extends MealServiceTest {

    @Test
    public void getWithUser() {
        Meal expected = MealTestData.getNew();
        expected.setUser(UserTestData.guest);
        service.create(expected, UserTestData.guest.id());
        expected.setUser(UserTestData.guest);
        Meal actual = service.getWithUser(expected.getId(), UserTestData.guest.id());
        MealTestData.MEAL_MATCHER_WITH_USER.assertMatch(actual, expected);
    }
}
