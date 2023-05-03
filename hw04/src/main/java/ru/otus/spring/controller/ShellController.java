package ru.otus.spring.controller;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.StudentService;
import ru.otus.spring.service.TestService;

@ShellComponent
public class ShellController {

    private final TestService testService;

    private final StudentService studentService;

    private Student student;

    public ShellController(TestService testService, StudentService studentService) {
        this.testService = testService;
        this.studentService = studentService;
    }

    @ShellMethod(value = "Login student", key = {"l", "login"})
    public void login() {
        student = studentService.getStudentFromConsole();
    }

    @ShellMethod(value = "Start testing", key = {"s", "start"})
    @ShellMethodAvailability(value = "isStudentLogin")
    public void startTest() {
        testService.startTest(student);
    }

    private Availability isStudentLogin() {
        String errorMessage = "Need enter student using command \"login\"";
        return student == null ? Availability.unavailable(errorMessage) : Availability.available();
    }
}
