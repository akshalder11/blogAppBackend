package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.Post;
import com.blogapp.blogappbackend.entity.PostReaction;
import com.blogapp.blogappbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final PostReactionService postReactionService;
    @Override
    public Post createPost(Post post) {
        post.setLikeCount(0);
        post.setDislikeCount(0);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID " + postId));

        // Get all reactions for the post
        List<PostReaction> reactions = postReactionService.getReactionByPost(postId);
        System.out.println("Reactions: " + reactions);
        // Count likes and dislikes
        long likes = reactions.stream().filter(r -> r.getReactionType() == PostReaction.ReactionType.LIKE).count();
        long dislikes = reactions.stream().filter(r -> r.getReactionType() == PostReaction.ReactionType.DISLIKE).count();

        // Update the post's reaction counts
        post.setLikeCount((int)likes);
        post.setDislikeCount((int)dislikes);

        boolean hasLiked = reactions.stream()
                .anyMatch(r -> r.getUserId().equals(userId)
                        && r.getReactionType() == PostReaction.ReactionType.LIKE);
        post.setHasLikedByCurrentUser(hasLiked);

        boolean hasDisLiked = reactions.stream()
                .anyMatch(r -> r.getUserId().equals(userId)
                        && r.getReactionType() == PostReaction.ReactionType.DISLIKE);
        post.setHasDisLikedByCurrentUser(hasDisLiked);

        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(Post post, Long postId) {
        Post updatedPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID " + postId));

        // Check time difference
        long minutesSinceCreation = java.time.Duration.between(updatedPost.getCreatedAt(), java.time.LocalDateTime.now()).toMinutes();
        if (minutesSinceCreation > 10) {
            throw new RuntimeException("Post can no longer be updated after 10 minutes of creation.");
        }

        updatedPost.setTitle(post.getTitle());
        updatedPost.setContent(post.getContent());
        updatedPost.setMediaType(post.getMediaType());
        updatedPost.setMediaUrl(post.getMediaUrl());
        updatedPost.setUpdatedAt(java.time.LocalDateTime.now());

        return postRepository.save(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post deletePost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID " + postId));
        postRepository.delete(deletePost);
    }

    @Override
    public List<Post> searchPostsByTitle(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Post> searchPostsByContent(String keyword) {
        return postRepository.searchContentNative(keyword);
    }
}
