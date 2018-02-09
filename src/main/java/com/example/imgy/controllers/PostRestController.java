package com.example.imgy.controllers;

import com.example.imgy.models.Post;
import com.example.imgy.models.data.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestController {

//    @Autowired
//    private PostDAO postDao;

//    @GetMapping("/posts")
//    public Iterable<Post> retrieveAllPosts() {
//        return postDao.findAll();
//    }
}
