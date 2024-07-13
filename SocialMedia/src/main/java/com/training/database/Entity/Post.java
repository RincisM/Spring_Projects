package com.training.database.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // A single user can have any number of posts
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    private String post;

    public Post(Users user, String post) {
        this.user = user;
        this.post = post;
    }
}
