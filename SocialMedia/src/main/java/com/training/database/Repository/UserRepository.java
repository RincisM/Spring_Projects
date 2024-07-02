package com.training.database.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.database.Entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{

    // User Repository Interface - An Abstract layer which interacts with the data in the Users_Table

    // Custome Method
    Optional<Users> findByUserName(String userName);
}
