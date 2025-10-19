package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.Post;
import com.blogapp.blogappbackend.entity.PostReaction;
import com.blogapp.blogappbackend.entity.PostReaction.ReactionType;
import com.blogapp.blogappbackend.repository.PostReactionRepository;
import com.blogapp.blogappbackend.repository.PostRepository;
import com.blogapp.blogappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReactionServiceImplementation implements PostReactionService {

    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostReaction reactToPost(Long postId, Long userId, String reactionTypeString) {
        //Validate
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID " + postId));

        if (post.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot react to your own post.");
        }

        // Convert string to enum safely
        ReactionType reactionType;
        try {
            reactionType = ReactionType.valueOf(reactionTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid reaction type: " + reactionTypeString);
        }

        // Check if user already reacted or else add reaction
        return postReactionRepository.findByUserIdAndPostId(userId, postId).map(
            existingReaction -> {
                existingReaction.setReactionType(reactionType);
                return postReactionRepository.save(existingReaction);
            }).orElseGet(()-> {
                PostReaction newReaction = PostReaction.builder()
                        .postId(postId)
                        .userId(userId)
                        .reactionType(reactionType)
                        .build();
                return postReactionRepository.save(newReaction);
        });
    }

    @Override
    public void removeReaction(Long postId, Long userId) {
        postReactionRepository.deleteByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<PostReaction> getReactionByPost(Long postId) {
        // Optional improvement: sort or filter later
        postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID " + postId));
        return postReactionRepository.findAll()
                .stream()
                .filter(r -> r.getPostId().equals(postId))
                .toList();
    }
}
