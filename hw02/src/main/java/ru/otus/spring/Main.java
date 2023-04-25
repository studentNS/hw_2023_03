package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.ApplicationRunner;

import java.io.IOException;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ApplicationRunner appRunner = context.getBean(ApplicationRunner.class);
        appRunner.run();
    }
}
