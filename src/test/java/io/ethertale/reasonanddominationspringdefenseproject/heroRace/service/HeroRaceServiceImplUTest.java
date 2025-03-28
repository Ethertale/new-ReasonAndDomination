package io.ethertale.reasonanddominationspringdefenseproject.heroRace.service;

import io.ethertale.reasonanddominationspringdefenseproject.heroRace.model.HeroRace;
import io.ethertale.reasonanddominationspringdefenseproject.heroRace.repo.HeroRaceRepo;
import io.ethertale.reasonanddominationspringdefenseproject.playerCharacter.repo.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroRaceServiceImplUTest {

    @Mock
    private HeroRaceRepo heroRepo;
    @InjectMocks
    private HeroRaceServiceImpl heroRaceService;

    @Test
    void  getAllHeroRaces_ReturnsAllHeroRaces() {
        HeroRace race1 = new HeroRace();
        HeroRace race2 = new HeroRace();
        HeroRace race3 = new HeroRace();

        heroRepo.save(race1);
        heroRepo.save(race2);
        heroRepo.save(race3);

        when(heroRepo.findAll()).thenReturn(List.of(race1, race2, race3));

        List<HeroRace> races = heroRaceService.getAllHeroRaces();

        assertThat(races.size()).isEqualTo(3);
        assertThat(races.get(0)).isEqualTo(race1);
        assertThat(races.get(1)).isEqualTo(race2);
        assertThat(races.get(2)).isEqualTo(race3);

        verify(heroRepo, times(1)).findAll();
    }
}