package com.example;

import com.example.utils.MD5Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewQuestionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterviewQuestionsApplication.class, args);
//        System.out.println(MD5Util.getMD5("1234"));
    }
}
