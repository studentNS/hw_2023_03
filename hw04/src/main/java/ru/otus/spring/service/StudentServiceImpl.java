package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final LocalizeService localizeService;

    public StudentServiceImpl(IOService ioService, LocalizeService localizeService) {
        this.ioService = ioService;
        this.localizeService = localizeService;
    }

    @Override
    public Student getStudentFromConsole() {
        ioService.outputString(localizeService.getMessage("firstname"));
        String firstName = ioService.readString();
        ioService.outputString(localizeService.getMessage("lastname"));
        String lastName = ioService.readString();
        return new Student(firstName, lastName);
    }
}
