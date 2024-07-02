package com.training.database.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.database.Entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
    Optional<Users> findByUserName(String userName);
}
