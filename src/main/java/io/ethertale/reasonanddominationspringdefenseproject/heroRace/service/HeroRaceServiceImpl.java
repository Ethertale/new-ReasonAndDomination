package io.ethertale.reasonanddominationspringdefenseproject.heroRace.service;

import io.ethertale.reasonanddominationspringdefenseproject.heroRace.model.HeroRace;
import io.ethertale.reasonanddominationspringdefenseproject.heroRace.repo.HeroRaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroRaceServiceImpl implements HeroRaceService {

    HeroRaceRepo heroRaceRepo;

    @Autowired
    public HeroRaceServiceImpl(HeroRaceRepo heroRaceRepo) {
        this.heroRaceRepo = heroRaceRepo;
    }

    @Override
    public List<HeroRace> getAllHeroRaces() {
        return heroRaceRepo.findAll();
    }
}
