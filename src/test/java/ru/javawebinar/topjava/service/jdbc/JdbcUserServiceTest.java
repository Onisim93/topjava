package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.List;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Test
    public void createUserWithRoles() {
        User u = new User();
        u.setRoles(new HashSet<>(){{add(Role.USER); add(Role.ADMIN);}});
        u.setName("Onisim");
        u.setEmail("Onisim93@gmail.com");
        u.setEnabled(true);
        u.setPassword("12345");
        u.setCaloriesPerDay(2000);

        User actual = service.create(u);
        User getUser = service.get(actual.id());

        Assert.assertEquals(getUser.toString(), actual.toString());
    }

    @Override
    public void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER_WITHOUT_ROLES.assertMatch(all, admin, guest, user);
    }
}