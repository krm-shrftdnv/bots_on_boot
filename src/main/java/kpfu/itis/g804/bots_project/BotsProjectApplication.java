package kpfu.itis.g804.bots_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class BotsProjectApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(BotsProjectApplication.class, args);
    }

}
