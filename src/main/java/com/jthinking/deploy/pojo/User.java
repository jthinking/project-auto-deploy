package com.jthinking.deploy.pojo;

/**
 * 用户
 * @author JiaBochao
 * @version 2017-11-22 09:56:47
 */
public class User {
    private String email;
    private String username;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

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
}
