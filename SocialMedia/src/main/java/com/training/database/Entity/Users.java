package com.training.database.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @Column(unique = true)
    private String userName;
    private String password;

    // Each user can have only one profile
    @OneToOne(cascade = CascadeType.ALL) // Any operations on the Users entity will also be applied to the associated profile entity
    @JoinColumn(name = "profile_id")
    private Profile profile;

    // A single user can have multiple posts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Users(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Add Post Method - For ensuring bidirectional relationship with Post
    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    // Remove Post Method - For ensuring bidirectional relationship with Post
    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }
}
