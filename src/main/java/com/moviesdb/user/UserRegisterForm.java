package com.moviesdb.user;

import javax.validation.constraints.Size;

public class UserRegisterForm {

    @Size(min = 6, max = 20, message = "Username must be more than 6 characters and less than 20 characters")
    private String username;

    @Size(min = 6, message = "Password must be more than 6 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
