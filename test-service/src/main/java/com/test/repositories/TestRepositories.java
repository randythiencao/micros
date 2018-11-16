package com.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entities.UserState;

public interface TestRepositories extends JpaRepository<UserState, Integer> {
	Optional<UserState> findByuserId(int userId);
}
