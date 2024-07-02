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
@Table(name="Post_Table")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    // Post Entity Class - Pojo class represents the fields of Post

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private int id; // Primary Key in the Post_Table

    private String userName;
    private String post;

    public Post(String userName, String post) {
        this.userName = userName;
        this.post = post;
    }
}
