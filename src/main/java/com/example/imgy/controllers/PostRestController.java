package com.example.imgy.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.imgy.exception.PostNotFoundException;
import com.example.imgy.models.Post;
import com.example.imgy.models.Comment;
import com.example.imgy.models.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.net.URI;

//comment out below CrossOrigin when testing with postman
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("posts")
public class PostRestController {

    @Autowired
    private PostDao postDao;

    @GetMapping("")
    public Iterable<Post> retrieveAllPosts() {
        return postDao.findAll();
    }

    @GetMapping("{id}")
    public Resource<Post> retrievePost(@PathVariable int id) {
        Post post = postDao.findOne(id);
        if (post == null)
            throw new PostNotFoundException("id-" + id);

        Resource<Post> resource = new Resource<Post>(post);

        ControllerLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllPosts());

        resource.add(linkTo.withRel("all-posts"));

        return resource;
    }

    @PostMapping("")
    public ResponseEntity<Object> createPost(@RequestBody Post post) {
        Post createdPost = postDao.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}/comments")
    public Iterable<Comment> retrieveAllPostComments(@PathVariable int id) {
        Post post = postDao.findOne(id);
        if (post == null)
            throw new PostNotFoundException("id-" + id);
        return post.getComments();
    }
}
