package com.example.admin.quanlysach.model;

public class Book {
    private String BookID;
    private String BookName;
    private String Genre_ID;
    private String Author;
    private String NXB;
    private String Price;
    private String Quantity;

    public Book(String bookID, String bookName, String genre_ID, String author, String NXB, String price, String quantity) {
        BookID = bookID;
        BookName = bookName;
        Genre_ID = genre_ID;
        Author = author;
        this.NXB = NXB;
        Price = price;
        Quantity = quantity;
    }

    public Book() {

    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getGenre_ID() {
        return Genre_ID;
    }

    public void setGenre_ID(String genre_ID) {
        Genre_ID = genre_ID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
