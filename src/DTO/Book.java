package DTO;

import java.util.List;

public class Book {

    private String isbn;

    private String title;

    private String author;

    public enum states {
        Avl,
        NotAvl,
        Lost
    };

    private states state;

    private List<Borrower> brwrs;


    //Constructors


    public Book(){

    }

    public Book(String isbn){

        this.isbn = isbn;

    }

    public Book(String isbn,String title,String author){

        this.isbn = isbn;
        this.title = title;
        this.author = author;

    }

    public Book(String isbn,String title,String author,states state){

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.state = state;

    }


    //Getters

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public states getState() {return this.state;}

    public List<Borrower> getBrwrs() {return this.brwrs;}

    //Setters

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setState(states state) {this.state = state;}

    public void setBrwrs(List<Borrower> brwrs) {this.brwrs = brwrs;}

}

