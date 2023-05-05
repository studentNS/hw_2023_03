package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public int readInt() {
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public String readString() {
        return sc.nextLine();
    }

    @Override
    public void outputString(String text) {
        System.out.println(text);
    }
}
