package com.example.mypageapplication.Model;

public class Data_One {
    String username;
    String email;
    String user_id;
    String background;
    String profileUrl;


    public Data_One(String username, String email, String user_id, String background, String profileUrl) {
        this.username = username;
        this.email = email;

        this.user_id = user_id;
        this.background = background;
        this.profileUrl = profileUrl;
    }

    public Data_One(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}