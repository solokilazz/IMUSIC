package com.example.da1.Models;

public class Commercial {
    private int id;
    private String title;
    private int image;
    private String songId;

    public Commercial() {
    }

    public Commercial(String title, int image, String songId) {
        this.title = title;
        this.image = image;
        this.songId = songId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
}
