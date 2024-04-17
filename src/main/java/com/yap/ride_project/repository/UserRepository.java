package com.yap.ride_project.repository;

import com.yap.ride_project.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
