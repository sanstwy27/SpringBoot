package com.atkjs927.springboot.repository;

import com.atkjs927.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Inherit JpaRepository to Operate
public interface UserRepository extends JpaRepository<User,Integer> {
}
