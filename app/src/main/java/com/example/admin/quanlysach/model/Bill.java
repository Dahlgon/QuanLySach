package com.example.admin.quanlysach.model;

public class Bill {

    private String Bill_ID;
    private long Date;
    public Bill(String bill_ID, long date) {
        Bill_ID = bill_ID;
        Date = date;
    }

    public Bill() {

    }

    public String getBill_ID() {
        return Bill_ID;
    }

    public void setBill_ID(String bill_ID) {
        Bill_ID = bill_ID;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }



}
