package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int publicationYear;
    private String author;
    private float price;
    private String genre;
    private boolean isInStock;
    private String imageLink;
    private String description;
    private boolean isThereAnBooksImage;
    private String booksImageLink;
    private String dateTime;


    public boolean isThereAnBooksImage() {
        return isThereAnBooksImage;
    }

    public void setThereAnBooksImage() {
        if (getBooksImageLink() == null || getBooksImageLink().equals("-")) {
            this.isThereAnBooksImage = false;
        } else {
            this.isThereAnBooksImage = true;
        }
    }

    public String getBooksImageLink() {
        return booksImageLink;
    }

    public void setBooksImageLink(String booksImageLink) {
        this.booksImageLink = booksImageLink;
        setThereAnBooksImage(); 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int year) {
        this.publicationYear = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
        this.dateTime = now.format(formatter);
    }
    public Book(String title, int year, String author, float price, String genre, boolean isInStock, String imageLink, String description, String booksImageLink) {
        this.title = title;
        this.publicationYear = year;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.isInStock = isInStock;
        this.imageLink = imageLink;
        this.description = description;
        this.booksImageLink = booksImageLink;
        setThereAnBooksImage();
        setDateTime();
    }

    public Book() {
    }
}