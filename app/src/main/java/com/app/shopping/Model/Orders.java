package com.app.shopping.Model;

public class Orders {
    private String idOrder, totalAmount, state, uID;

    public Orders(String idOrder, String totalAmount, String state, String uID) {
        this.idOrder = idOrder;
        this.totalAmount = totalAmount;
        this.state = state;
        this.uID = uID;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "idOrder='" + idOrder + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", state='" + state + '\'' +
                ", uID='" + uID + '\'' +
                '}';
    }
}
