package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main1.class, args);
    }

    @GetMapping("/")
    public String home() {
        String s1="My name is amit";
        return s1;
    }
    @GetMapping("/about")
    public String about(){
        String s2="Jangra";
        return s2;
    }
}