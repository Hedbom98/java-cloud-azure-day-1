package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
