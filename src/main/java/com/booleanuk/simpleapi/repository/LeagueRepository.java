package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Integer> {
}
