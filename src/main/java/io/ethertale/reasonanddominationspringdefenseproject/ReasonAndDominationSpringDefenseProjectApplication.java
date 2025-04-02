package io.ethertale.reasonanddominationspringdefenseproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication()
public class ReasonAndDominationSpringDefenseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReasonAndDominationSpringDefenseProjectApplication.class, args);
    }

}
