package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
