package com.blogapp.blogappbackend.repository;

import com.blogapp.blogappbackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Fetch all posts by a user
    List<Post> findByUserId(Long userId);

    // Search posts by title (for search API)
    List<Post> findByTitleContainingIgnoreCase(String title);

    // Search posts by content (for search API)
    // List<Post> findByContentContainingIgnoreCase(String content);
    @Query(value = "SELECT * FROM posts WHERE content LIKE %:keyword%", nativeQuery = true)
    List<Post> searchContentNative(@Param("keyword") String keyword);

}
