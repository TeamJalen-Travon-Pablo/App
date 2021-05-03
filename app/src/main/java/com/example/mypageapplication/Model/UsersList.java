package com.example.mypageapplication.Model;

public class UsersList {
    String full_name,email,password,uid,profile;

    public UsersList(String full_name, String email, String password, String uid, String profile) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.profile = profile;
    }
    public UsersList() {

    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
