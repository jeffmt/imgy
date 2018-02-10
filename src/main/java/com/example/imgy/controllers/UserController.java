package com.example.imgy.controllers;

import com.example.imgy.exception.PostNotFoundException;
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
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @GetMapping("")
    public Iterable<User> retrieveAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        return user;
    }

    @PostMapping("")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        System.out.println("in UserController, user is : " + user);
        User createdUser = userDao.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable int id) {
        userDao.delete(id);
    }

    @GetMapping("{id}/posts")
    public List<Post> retrieveUserPosts(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        return user.getPosts();
    }

    @DeleteMapping("{user_id}/posts/{post_id}")
    public void deletePost(@PathVariable int user_id, @PathVariable int post_id) {
        Post post = postDao.findOne(post_id);
        if (post == null)
            throw new PostNotFoundException("post_id-" + post_id);

        if (post.getUser().getId() != user_id)
            throw new UserNotFoundException("user_id-" + user_id);

        postDao.delete(post_id);
    }

    @PostMapping("{id}/posts")
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
