package com.example.imgy.controllers;

import com.example.imgy.models.Post;
import com.example.imgy.models.data.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestController {

    @Autowired
    private PostDAO postDao;

    @GetMapping("/posts")
    public Iterable<Post> retrieveAllPosts() {
//    public List<Post> retrieveAllPosts() {
        System.out.println("In here yall");


//        Iterable<Post> posts = postDao.findAll();


        return postDao.findAll();
    }

    @PostMapping("/posts")
    public void createPost(@RequestBody Post post) {
        Post createdPost = postDao.save(post);
    }
}
