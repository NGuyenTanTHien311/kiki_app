package com.example.kiki;

public class User {

    String truyen, tacgia;
    long chapter;

     public User(){}



    public User(String truyen, String tacgia, long chapter) {
        this.truyen = truyen;
        this.tacgia = tacgia;
        this.chapter = chapter;
    }

    public String getTruyen() {
        return truyen;
    }

    public void setTruyen(String truyen) {
        this.truyen = truyen;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public long getChapter() {
        return chapter;
    }

    public void setChapter(long chapter) {
        this.chapter = chapter;
    }
}
