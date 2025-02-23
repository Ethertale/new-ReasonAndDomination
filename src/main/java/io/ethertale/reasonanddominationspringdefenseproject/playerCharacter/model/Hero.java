package io.ethertale.reasonanddominationspringdefenseproject.playerCharacter.model;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, name = "character_class")
    private HeroClasses characterClass;
    @Column(nullable = false)
    private int level;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeroRaces race;
    @ManyToOne
    private Profile profile;

    public Hero() {
    }

    public Hero(UUID id, String name, HeroClasses characterClass, int level, HeroRaces race, Profile profile) {
        this.id = id;
        this.name = name;
        this.characterClass = characterClass;
        this.level = level;
        this.race = race;
        this.profile = profile;
    }

    public HeroRaces getRace() {
        return race;
    }

    public void setRace(HeroRaces race) {
        this.race = race;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroClasses getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(HeroClasses characterClass) {
        this.characterClass = characterClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Profile getAccount() {
        return profile;
    }

    public void setAccount(Profile profile) {
        this.profile = profile;
    }
}
