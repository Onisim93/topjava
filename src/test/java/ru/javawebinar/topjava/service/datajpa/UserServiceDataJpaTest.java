package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

@ActiveProfiles("datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User expected = UserTestData.user;
        List<Meal> expectedMeals = MealTestData.getUserSortedMeals();
        expected.setMeals(expectedMeals);
        User actual = service.getWithMeals(UserTestData.user.id());
        UserTestData.USER_MATCHER.assertMatch(actual, expected);
        MealTestData.MEAL_MATCHER.assertMatch(actual.getMeals(), expectedMeals);
    }
}
