package ru.otus.spring.service;

public class OutputServiceImpl implements OutputService{

    @Override
    public void outputString(String s){
        System.out.println(s);
    }

}
