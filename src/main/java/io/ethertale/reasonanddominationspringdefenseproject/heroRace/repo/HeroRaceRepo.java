package io.ethertale.reasonanddominationspringdefenseproject.heroRace.repo;

import io.ethertale.reasonanddominationspringdefenseproject.heroRace.model.HeroRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRaceRepo extends JpaRepository<HeroRace, Integer> {
}
