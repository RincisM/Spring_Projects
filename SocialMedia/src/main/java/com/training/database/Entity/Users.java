package com.training.database.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users_Table")
@Getter
@Setter
@NoArgsConstructor
public class Users {

    // Users Entity Class - Pojo class represents the fields of Users

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id; // Primary Key in the Users_Table

    private String userName;
    private String password;

    public Users(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
