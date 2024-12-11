package com.example;

import com.example.utils.MD5Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class InterviewQuestionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterviewQuestionsApplication.class, args);
        System.out.println(UUID.randomUUID().toString());
    }
}
