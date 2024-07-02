package com.training.database.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.database.Entity.Profile;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{

    // Profile Repository Interface - An Abstract layer which interacts with the data in the Profile_Table

    // Custom methods
    Optional<Profile> findByEmail(String email);
    Optional<Profile> findByUserName(String username);
}
