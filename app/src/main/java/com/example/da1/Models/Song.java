package com.example.da1.Models;

import java.io.Serializable;

public class Song implements Serializable {
    private int _id;
    private String name;
    private int image;
    private int link;
    private String styleId;
    private String singerId;
    private int count;
    private boolean status;

    public Song(int _id, String name, int image, int link, String styleId, String singerId, int count, boolean status) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.link = link;
        this.styleId = styleId;
        this.singerId = singerId;
        this.count = count;
        this.status = status;
    }

    public Song(String name, int image, int link, String styleId, String singerId, int count, boolean status) {
        this.name = name;
        this.image = image;
        this.link = link;
        this.styleId = styleId;
        this.singerId = singerId;
        this.count = count;
        this.status = status;
    }


    public Song() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
