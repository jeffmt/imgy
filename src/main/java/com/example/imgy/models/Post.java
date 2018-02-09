package com.example.imgy.models;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;

    private String description;

    private int likes;
    private int views;
    private int points;

    private Date created;

    //@ManyToMany(mappedBy = "posts")
    @ManyToMany
    private List<Tag> tags;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();

    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] image;
    //private Blob image;

    public Post() {
    }

    public Post(String description) {
        this.description = description;
        this.likes = 0;
        this.views = 0;
        this.points = 0;
        this.created = new Date();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void incrementLikes() {
        this.likes++;
    }

    public int getViews() {
        return views;
    }

    public Date getCreated() {
        return created;
    }

    public void incrementViews() {
        this.views++;
    }

    public int getPoints() {
        return points;
    }

    public void incrementPoints() {
        this.points++;
    }

    /*
    public byte[] getPhoto() {

        byte[] imgData = null;
        Blob img = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {

            conn = getConnection();
            String sqlQ = "SELECT CONTENT_FILE FROM CONTENT where id = 'something';
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQ);

            while (rs.next()) {
                img = rs.getBlob("CONTENT_FILE");
                imgData = img.getBytes(1, (int) img.length());
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return imgData;
        }
    }
    */
}
