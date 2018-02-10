package com.example.imgy.controllers;

import com.example.imgy.exception.UserNotFoundException;
import com.example.imgy.models.Post;
import com.example.imgy.models.User;
import com.example.imgy.models.data.PostDao;
import com.example.imgy.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @GetMapping("/users")
    public Iterable<User> retrieveAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrievePost(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        System.out.println("in UserController, user is : " + user);
        User createdUser = userDao.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveUserPosts(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @Valid @RequestBody Post post) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        post.setUser(user);
        postDao.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
