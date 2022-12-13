package com.example.lyricslover;

import java.io.Serializable;

public class Song implements Serializable {
    private String mName;
    private String mAuthor;
    private String mImageURL;
    private int mId;

    public Song(String name, String author, String imageURL, int id)
    {
        this.mName = name;
        this.mAuthor = author;
        this.mImageURL = imageURL;
        this.mId = id;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    public String getAuthor() {
        return mAuthor;
    }
    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getImageURL() {
        return mImageURL;
    }
    public void setImageURL(String imageURL) {
        this.mImageURL = imageURL;
    }

    public int getId() {
        return mId;
    }
    public void setId(int id) {
        this.mId = id;
    }

}
