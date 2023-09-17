package DTO;


import java.util.List;

public class Borrower {

    private int id;

    private String name;

    private String cin;

    private String phoneNumber;

    private List<Book> books;


    //Constructors

    public Borrower(){

    }

    public Borrower(int id){

        this.id = id;

    }

    public Borrower(String name, String cin, String phoneNumber){

        this.name = name;
        this.cin = cin;
        this.phoneNumber = phoneNumber;

    }

    public Borrower(int id, String name, String cin, String phoneNumber){

        this.id = id;
        this.name = name;
        this.cin = cin;
        this.phoneNumber = phoneNumber;

    }

    //Getters

    public int getId() {
        return this.id;
    }

    public String getCin() {
        return this.cin;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public List<Book> getBooks() {return books;}

//Setters

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBooks(List<Book> books) {this.books = books;}
}

