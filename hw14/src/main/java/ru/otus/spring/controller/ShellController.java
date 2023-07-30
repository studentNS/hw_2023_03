package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final JobLauncher jobLauncher;

    private final Job jobMigrateFomMongo;

    private final JobExplorer jobExplorer;

    private final JobOperator jobOperator;

    @ShellMethod(value = "migrateFromMongo", key = "start")
    public void startMigrationFromMongo() throws Exception {
        JobExecution execution = jobLauncher.run(jobMigrateFomMongo, new JobParameters());
        System.out.println(execution);
    }

    @ShellMethod(value = "restartMigrateFromMongo", key = "restart")
    public void restartMigrateFromMongo() throws Exception {
        JobInstance lastJobInstance = jobExplorer.getLastJobInstance(jobMigrateFomMongo.getName());
        if (lastJobInstance == null) {
            throw new IllegalStateException("Nothing to restart");
        }
        Long executionId = jobOperator.startNextInstance(lastJobInstance.getJobName());
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(jobMigrateFomMongo.getName()));
    }
}
