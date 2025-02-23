package io.ethertale.reasonanddominationspringdefenseproject;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.playerCharacter.model.Hero;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReasonAndDominationSpringDefenseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReasonAndDominationSpringDefenseProjectApplication.class, args);
    }

}
