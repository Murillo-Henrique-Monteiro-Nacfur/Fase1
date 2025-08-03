package com.postech.fiap.fase1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class TechChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechChallengeApplication.class, args);
    }

    static {
        Locale.setDefault(Locale.US);
    }
}
