package ru.javawebinar.topjava.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.SecurityUtil;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-app.xml");

        MealRepository repository = context.getBean(MealRepository.class);

        System.out.println(repository.getAll(SecurityUtil.authUserId()));


    }
}
