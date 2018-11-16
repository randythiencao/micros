package com.test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entities.UserState;
import com.test.entities.UserStateId;

public interface TestRepositories extends JpaRepository<UserState, UserStateId> {
	Optional<List<UserState>> findByuserStateIdUserId(int userId);
}
