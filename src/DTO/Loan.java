package DTO;


public class Loan {

    private Book book;

    private Borrower borrower;

    private String loanDate;

    private int days;

    //Constructors

    public Loan(){

    }

    public Loan(Book book){

        this.book = book;

    }

    public Loan(Book book, Borrower borrower, int days){

        this.book = book;
        this.borrower = borrower;
        this.days = days;

    }

    public Loan(Book book, Borrower borrower, String loanDate, int days){

        this.book = book;
        this.borrower = borrower;
        this.loanDate = loanDate;
        this.days = days;

    }

    //Getters

    public Book getBook() {
        return this.book;
    }

    public Borrower getBorrower() {
        return this.borrower;
    }

    public String getLoanDate() {
        return this.loanDate;
    }

    public int getDays() {
        return this.days;
    }

    //Setters

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public void setDays(int days) {
        this.days = days;
    }

}

