package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student getStudentFromConsole() {
        ioService.outputString("Firstname: ");
        String firstName = ioService.readString();
        ioService.outputString("Lastname: ");
        String lastNname = ioService.readString();

        return new Student(firstName, lastNname);
    }

}
