package com.example.imgy.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    private int role_id;

    @NotNull
    @Column(unique=true)
    @Size(min=2, message = "Username should be at least 2 characters long")
    private String username;

    @Email
    private String email;

    @Size(min=6, message = "Password must be at least 6 characters long")
    @NotNull
    @JsonIgnore
    private String password;

//    @ManyToOne
//    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Comment> comments = new ArrayList<>();

    public User() {
        System.out.println("in User's default constructor");
    }

    public List<Post> getPosts() {
        return posts;
    }

    public int getId() {
        return id;
    }

    public User(String username, String email, String password) {
        System.out.println("in User's non default constructor");
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                ", comments=" + comments +
                '}';
    }
}
