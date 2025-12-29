package com.fitnesstrackerbackend;

import org.springframework.boot.SpringApplication;

public class TestFitnessTrackerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(FitnessTrackerBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
