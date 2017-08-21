package com.example.enduser.library;

/**
 * Created by EndUser on 8/18/2017.
 */

public class Book {
    private String imageURl;
    private String title;
    private String author;
    private String description;
    public Book(String title ,String author , String description, String string){
        this.title = title;
        this.author = author;
        this.description = description;
        imageURl = string;

    }
    public Book(String title ,String author , String description){
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getImageURl() {
        return imageURl;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
