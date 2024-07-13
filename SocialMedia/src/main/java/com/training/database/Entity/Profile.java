package com.training.database.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Profile_Table")
@Getter
@Setter
public class Profile {

    // Profile Entity Class - Pojo class represents the fields of Profile

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private int id; // Primary Key of the Profile_Table

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Date dateOfBirth;
    private int age;

    // Each profile entity is associated with exactly one Users entity
    @OneToOne(mappedBy = "profile")
    private Users users;
}
