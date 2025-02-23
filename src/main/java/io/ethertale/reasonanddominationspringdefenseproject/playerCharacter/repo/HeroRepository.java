package io.ethertale.reasonanddominationspringdefenseproject.playerCharacter.repo;

import io.ethertale.reasonanddominationspringdefenseproject.playerCharacter.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HeroRepository extends JpaRepository<Hero, UUID> {
}
