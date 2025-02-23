package io.ethertale.reasonanddominationspringdefenseproject.heroRace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HeroRace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private int strength;
    @Column
    private int agility;
    @Column
    private int stamina;
    @Column
    private int intellect;
    @Column
    private int spirit;


}
