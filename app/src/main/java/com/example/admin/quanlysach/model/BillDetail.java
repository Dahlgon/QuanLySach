package com.example.admin.quanlysach.model;

public class BillDetail {
    private Integer billDetailID;
    private String billID,bookID,quantity;

    public Integer getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(Integer billDetailID) {
        this.billDetailID = billDetailID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getBillID() {
        return billID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
