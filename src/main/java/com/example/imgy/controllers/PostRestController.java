package com.example.imgy.controllers;

import com.example.imgy.PostNotFoundException;
import com.example.imgy.models.Post;
import com.example.imgy.models.data.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PostRestController {

    @Autowired
    private PostDAO postDao;

    @GetMapping("/posts")
    public Iterable<Post> retrieveAllPosts() {
        return postDao.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post retrievePost(@PathVariable int id) {
        Post post = postDao.findOne(id);
        if (post == null)
            throw new PostNotFoundException("id-" + id);
        return post;
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody Post post) {
        Post createdPost = postDao.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
