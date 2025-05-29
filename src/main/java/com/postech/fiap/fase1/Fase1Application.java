package com.postech.fiap.fase1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class Fase1Application {

    public static void main(String[] args) {
        SpringApplication.run(Fase1Application.class, args);
    }

    static {
        Locale.setDefault(Locale.US);
    }
}
