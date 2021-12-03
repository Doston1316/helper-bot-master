package uz.projavadev.helperbotapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class HelperBotAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelperBotAppApplication.class, args);
    }
}
