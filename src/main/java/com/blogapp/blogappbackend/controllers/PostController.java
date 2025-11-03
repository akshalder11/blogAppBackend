package com.blogapp.blogappbackend.controllers;

import com.blogapp.blogappbackend.entity.Post;
import com.blogapp.blogappbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // CREATE
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            Post savePost = postService.createPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(savePost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET ALL
    @GetMapping("/allPost")
    public ResponseEntity<?> getAllPosts () {
        try {
            List<Post> postList = postService.getAllPosts();
            return ResponseEntity.ok(postList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET BY ID
    @PostMapping("/getPost")
    public ResponseEntity<?> getPostById (@RequestBody Map<String, String> payload) {
        try {
            Long postId = Long.parseLong(payload.get("postId"));
            Long userId = Long.parseLong(payload.get("userId"));
            Post post = postService.getPostById(postId, userId);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // UPDATE
    @PutMapping("/updatePost")
    public ResponseEntity<?> updatePost (@RequestBody Map<String, Object > payload) {
        try {
            //Extract from payload
            Long postId = Long.parseLong(payload.get("postId").toString());
            String title = payload.get("title").toString();
            String content = payload.get("content").toString();
            String mediaTypeStr = payload.get("mediaType").toString();
            //String mediaUrls = payload.get("mediaUrls").toString();
            List<String> mediaUrls = ((List<?>) payload.get("mediaUrls")).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());;

            //Set to buffer object
            Post updatedPost = new Post();
            updatedPost.setTitle(title);
            updatedPost.setContent(content);
            updatedPost.setMediaType(Post.MediaType.valueOf(mediaTypeStr));
            updatedPost.setMediaUrls(mediaUrls);

            //Final update
            Post postToUpdate = postService.updatePost(updatedPost, postId);
            return ResponseEntity.ok(postToUpdate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE
    @DeleteMapping("/deletePost")
    public ResponseEntity<?> deletePost (@RequestBody Map<String, String> payload) {
        try {
            Long postId = Long.parseLong(payload.get("postId"));
            postService.deletePost(postId);
            return ResponseEntity.ok(Map.of("message", "Post deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // SEARCH BY TITLE
    @GetMapping("/search/title")
    public ResponseEntity<?> searchByTitle (@RequestBody Map<String, String> payload) {
        try {
            List<Post> searchList = postService.searchPostsByTitle(payload.get("title"));
            return ResponseEntity.ok(searchList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // SEARCH BY TITLE
    @GetMapping("/search/content")
    public ResponseEntity<?> searchByContent (@RequestBody Map<String, String> payload) {
        try {
            List<Post> searchList = postService.searchPostsByContent(payload.get("content"));
            return ResponseEntity.ok(searchList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
