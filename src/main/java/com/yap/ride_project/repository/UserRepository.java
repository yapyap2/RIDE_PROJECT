package com.yap.ride_project.repository;

import com.yap.ride_project.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"bikeType"})
    List<User> findAll();

    @EntityGraph(attributePaths = {"bikeType"})
    Optional<User> findUserByUserId(Long id);
}
