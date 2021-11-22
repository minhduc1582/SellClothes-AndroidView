package com.app.shopping.Model;

import lombok.*;

@Getter
@Setter
public class User {
    private String name;
    private String phone;
    private String password;
    private int level;
    private String avatar;
    public User()
    {

    }

    public User(String name, String phone, String password, int level,String avatar) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.level = level;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
