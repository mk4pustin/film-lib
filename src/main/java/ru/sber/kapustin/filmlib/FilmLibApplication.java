package ru.sber.kapustin.filmlib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FilmLibApplication implements CommandLineRunner {
    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(String... args) throws Exception {
        var encoder = new BCryptPasswordEncoder();
        var password = encoder.encode("admin");
        System.out.println(password);

        System.out.println("Swagger path: http://localhost:" + serverPort + "/swagger-ui/index.html");
        System.out.println("Application path: http://localhost:" + serverPort);

    }

    public static void main(String[] args) {
        SpringApplication.run(FilmLibApplication.class, args);
    }
}
