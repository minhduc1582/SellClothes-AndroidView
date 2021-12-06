package com.app.shopping.Model;

public class Orders {
    private String idOrder, totalAmount, state, uid;

//    public Orders(String idOrder, String totalAmount, String state, String uid) {
//        this.idOrder = idOrder;
//        this.totalAmount = totalAmount;
//        this.state = state;
//        this.uid = uid;
//    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "idOrder='" + idOrder + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", state='" + state + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
