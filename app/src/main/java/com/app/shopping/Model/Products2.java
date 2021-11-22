package com.app.shopping.Model;



public class Products2 {
    private String id, name, descriptiion, price, image, idcategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptiion() {
        return descriptiion;
    }

    public void setDescriptiion(String descriptiion) {
        this.descriptiion = descriptiion;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    @Override
    public String toString() {
        return "Products2{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", descriptiion='" + descriptiion + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", idcategory='" + idcategory + '\'' +
                '}';
    }
}
