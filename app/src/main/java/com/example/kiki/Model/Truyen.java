package com.example.kiki.Model;

public class Truyen {
    private String ID, chapter;
    private String name, author, imagePath, price;

    public Truyen() {
    }

    public Truyen(String ID , String chapter , String name , String author , String imagePath, String price) {
        this.ID = ID;
        this.chapter = chapter;
        this.name = name;
        this.author = author;
        this.imagePath = imagePath;
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
