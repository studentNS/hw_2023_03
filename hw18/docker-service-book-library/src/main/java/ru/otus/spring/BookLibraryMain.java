package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BookLibraryMain {
    public static void main(String[] args) {
        SpringApplication.run(BookLibraryMain.class, args);
    }
}
