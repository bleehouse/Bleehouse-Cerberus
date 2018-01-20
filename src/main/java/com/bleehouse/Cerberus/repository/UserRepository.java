package com.bleehouse.Cerberus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bleehouse.Cerberus.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String username);

}
