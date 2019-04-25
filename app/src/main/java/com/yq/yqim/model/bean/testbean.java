package com.yq.yqim.model.bean;

public class testbean {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public testbean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public testbean() {
    }
}
