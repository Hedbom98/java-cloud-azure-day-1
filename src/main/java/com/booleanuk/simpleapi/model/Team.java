package com.booleanuk.simpleapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int founded;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties({"id", "parentClub", "team"})
    private List<Player> players;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"id", "age"})
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "league_id")
    @JsonIgnoreProperties({"teams", "id", "country"})
    private League league;

    public Team(String name, int founded, Manager manager, League league){
        this.name = name;
        this.founded = founded;
        this.manager = manager;
        this.league = league;
    }
}
