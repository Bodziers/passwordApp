package pl.finmatik.passwordapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.GsonJsonParser;
import pl.finmatik.passwordapp.model.Password;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PasswordAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasswordAppApplication.class, args);
    }
}
