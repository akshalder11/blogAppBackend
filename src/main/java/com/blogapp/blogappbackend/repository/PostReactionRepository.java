package com.blogapp.blogappbackend.repository;

import com.blogapp.blogappbackend.entity.PostReaction;
import com.blogapp.blogappbackend.entity.PostReaction.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

    // Find reaction by user & post
    Optional<PostReaction> findByUserIdAndPostId(Long userId, Long postId);

    // Count likes/dislikes for a post
    long countByPostIdAndReactionType(Long postId, ReactionType reactionType);

    // Delete reaction by user & post
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
