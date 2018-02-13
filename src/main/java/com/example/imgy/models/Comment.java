package com.example.imgy.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String text;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    private Date created;

    public Comment() {
    }

    public Comment(String text, Post post, User user) {
        this.text = text;
        this.post = post;
        this.user = user;
        this.created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public String getText() {
        return text;
    }

    public Post getPost() {
        return post;
    }

    public int getId() {
        return id;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public void setText(String text) {

        this.text = text;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }
}
