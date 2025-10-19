package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.Post;
import com.blogapp.blogappbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found with ID " + postId));
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
