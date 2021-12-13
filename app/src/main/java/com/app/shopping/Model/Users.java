package com.app.shopping.Model;

public class Users {
    private String name, phone, password, image, address, level;
    public Users()
    {

    }

//    public Users(String name, String phone, String password, String image, String address) {
//        this.name = name;
//        this.phone = phone;
//        this.password = password;
//        this.image = image;
//        this.address = address;
//    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
