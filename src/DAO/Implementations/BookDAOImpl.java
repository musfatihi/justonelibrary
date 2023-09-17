package DAO.Implementations;

import DAO.BookManager;
import DAO.Interfaces.BookDAO;
import DAO.LoanManager;
import DTO.Book;
import DTO.Loan;
import UTIL.Utilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookDAOImpl implements BookDAO{

    public static BookManager bookManager;
    public static LoanManager loanManager;

    //Constructor-based Dependency Injection
    public BookDAOImpl(BookManager bookManager,LoanManager loanManager){

        BookDAOImpl.bookManager = bookManager;
        BookDAOImpl.loanManager = loanManager;

    }

    @Override
    //Book Adding
    public Book add(Book book) {

        if(bookManager.checkBook(book)){

            Utilities.displayErrorMsg("Le livre dont l'isbn "+book.getIsbn()+" existe déja!!");
            return null;

        }

        if(!bookManager.addBook(book)){

            Utilities.displayErrorMsg("Error!!");
            return null;

        }

        return book;

    }

    @Override
    //Book Infos Updating
    public Book update(Book book) {

        if(!bookManager.checkBook(book)){

            Utilities.displayErrorMsg("Le livre dont l'isbn "+book.getIsbn()+" n'est pas enregistré!!");
            return null;

        }

        if(!bookManager.updateBook(book)){

            Utilities.displayErrorMsg("Error!!");
            return null;

        }

        return book;

    }

    //Getting Book Infos
    public Book getInfos(Book book) {

        if(!bookManager.checkBook(book)){

            Utilities.displayErrorMsg("Le livre dont l'isbn "+book.getIsbn()+" n'est pas enregistré!!");
            return null;

        }

        return bookManager.getBookInfos(book);

    }

    //Book Deletion
    public static Boolean delete(Book book,BookManager bookManager){

        if(!bookManager.checkBook(book)){

            Utilities.displayErrorMsg("Le livre dont l'isbn "+book.getIsbn()+" n'est pas enregistré!!");
            return false;

        }

        if(bookManager.deleteBook(book)){

            return true;

        }

        return false;

    }

    //Show All Books
    public static List<Book> showAll(BookManager bookManager) {

        return bookManager.showAllBooks();

    }

    //Show Borrowed Books
    public static List<Loan> showBrwds(BookManager bookManager,LoanManager loanManager) {

        List<Loan> loans = new ArrayList<>();

        List<Book> brwdBooks = bookManager.showBooks(Book.states.NotAvl.name());

        for (Book book : brwdBooks) {

            Loan loan = loanManager.getLoanInfos(book);
            loans.add(loan);

        }

        return loans;

    }

    //Search Books By Title
    public static List<Book> searchByTitle(String title,BookManager bookManager) {
        return bookManager.searchBook("title",title);
    }

    //Search Books By Author
    public static List<Book> searchByAuthor(String author,BookManager bookManager) {
        return bookManager.searchBook("author",author);
    }

    //Books Statistics
    public static HashMap<String,String> getStatistics(BookManager bookManager) {

        return bookManager.getStatistics();

    }

}
