package com.tarakan.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int year;
    private final int quantity;
    private final double price;
    private int status;

    @JsonCreator
    public Book(@JsonProperty("int") int id,@JsonProperty("title") String title,@JsonProperty("author") String author,@JsonProperty("year") int year,@JsonProperty("quantity") int quantity,@JsonProperty("price") double price,@JsonProperty("status") int status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nBook{id:" + id +
                ", title:'" + title + "'" +
                ", author:'" + author + "'" +
                ", year:" + year +
                ", quantity:" + quantity +
                ", price:" + price +
                ", status:" + status + "}";
    }
}
