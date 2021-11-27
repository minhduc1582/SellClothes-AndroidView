package com.app.shopping.Model;

public class DetailOrder {
    private String idDetailorder,pid,quantity,idOrder;

    public DetailOrder(String idDetailorder, String pid, String quantity, String idOrder) {
        this.idDetailorder = idDetailorder;
        this.pid = pid;
        this.quantity = quantity;
        this.idOrder = idOrder;
    }

    public String getIdDetailorder() {
        return idDetailorder;
    }

    public void setIdDetailorder(String idDetailorder) {
        this.idDetailorder = idDetailorder;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public String toString() {
        return "DetailOrder{" +
                "idDetailorder='" + idDetailorder + '\'' +
                ", pid='" + pid + '\'' +
                ", quantity='" + quantity + '\'' +
                ", idOrder='" + idOrder + '\'' +
                '}';
    }
}
