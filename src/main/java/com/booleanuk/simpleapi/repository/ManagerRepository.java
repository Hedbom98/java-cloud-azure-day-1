package com.booleanuk.simpleapi.repository;

import com.booleanuk.simpleapi.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
