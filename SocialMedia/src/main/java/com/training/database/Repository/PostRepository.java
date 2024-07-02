package com.training.database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.database.Entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    // Post Repository Interface - An Abstract layer which interacts with the data in the Post_Table
}
