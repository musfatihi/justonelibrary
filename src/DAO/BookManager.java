package DAO;

import DTO.Book;
import UTIL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookManager {

    private Connection connection;

    private static final String ADD_BOOK = "insert into books (isbn,title,author) values (?,?,?)";
    private static final String UPDATE_BOOK = "update books set title = ?, author = ? where isbn=?";
    private static final String GET_BOOK = "select * from books where isbn=?";
    private static final String SHOW_BOOKS = "select * from books where state=?";
    private static final String SHOW_BOOKS_NOT = "select * from books where state<>?";
    private static final String SHOW_ALL_BOOKS = "select * from books";
    private static final String SEARCH_BOOKS = "select * from books where ";
    private static final String DELETE_BOOK = "delete from books where isbn=?";
    private static final String STATISTICS = "select state,count(*) as nbr from books group by state";
    private static final String CHECK_STATE = "select * from books where isbn=? AND state=?";
    private static final String UPDATE_STATE = "update books set state = ? where isbn=?";
    private static final String CHECK_BOOK = "select * from books where isbn=?";
    private static final String UPDATE_STATE_BOOKS = "update books SET state='Lost' " +
            "WHERE isbn IN (SELECT DISTINCT isbn FROM (SELECT * FROM loans ORDER BY loandate DESC) AS SR " +
            "WHERE loandate::date+ days * INTERVAL '1 day'<CURRENT_DATE::date) " +
            "AND state='NotAvl'";


    //Constructor
    public BookManager(Connection connection) {

        this.connection = connection;
        //this.updateBooksState();

    }



    //Book Adding
    public Boolean addBook(Book book) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(ADD_BOOK);
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());

            int rowsAdded = stmt.executeUpdate();

            return rowsAdded > 0;

        } catch (Exception e) {

            System.out.println(e);

        }

        return false;

    }

    //Book Infos Updating
    public Boolean updateBook(Book book) {

        int rowsUpdated=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_BOOK);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());

            rowsUpdated = stmt.executeUpdate();


        } catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated > 0;

    }

    //Getting Book Infos
    public Book getBookInfos(Book book) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(GET_BOOK);

            stmt.setString(1, book.getIsbn());

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                book.setTitle(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setState(Book.states.valueOf(resultSet.getString(4)));

            }

        } catch (Exception e) {

            System.out.println(e);
            return null;

        }

        return book;

    }

    //State-based Books Filtering
    public List<Book> showBooks(String state) {

        List<Book> books = new ArrayList<Book>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SHOW_BOOKS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, state);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Book book = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),Book.states.valueOf(resultSet.getString(4)));
                books.add(book);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return books;

    }

    //State-based Books Filtering Not EQUAL
    public List<Book> showBooksNot(String state) {

        List<Book> books = new ArrayList<Book>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SHOW_BOOKS_NOT, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, state);


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Book book = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),Book.states.valueOf(resultSet.getString(4)));
                books.add(book);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return books;

    }

    //Show All Books
    public List<Book> showAllBooks() {

        List<Book> books = new ArrayList<Book>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SHOW_ALL_BOOKS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Book book = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),Book.states.valueOf(resultSet.getString(4)));
                books.add(book);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return books;

    }

    //Search Books
    public List<Book> searchBook(String field,String value) {

        List<Book> books = new ArrayList<Book>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SEARCH_BOOKS+field+"=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, value);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Book book = new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),Book.states.valueOf(resultSet.getString(4)));
                books.add(book);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return books;

    }

    //Book Deletion
    public Boolean deleteBook(Book book){

        int rowsDeleted = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_BOOK);

            stmt.setString(1,book.getIsbn());

            rowsDeleted = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsDeleted>0;

    }

    //Book Statistics
    public HashMap<String,String> getStatistics(){

        HashMap<String, String> statistics = new HashMap<String, String>();
        int Total=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(STATISTICS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){

                statistics.put(resultSet.getString(1), String.valueOf(resultSet.getInt(2)));
                Total+=resultSet.getInt(2);

            }

                statistics.put("Total", String.valueOf(Total));

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return statistics;

    }

    //Book State Checking
    public Boolean checkBookState(Book book,String state){

        int rowCount=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_STATE,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, state);

            ResultSet resultSet = stmt.executeQuery();

            rowCount = resultSet.last() ? resultSet.getRow() : 0;

        } catch (Exception e) {

            System.out.println(e);

        }

        return rowCount>0;

    }

    //Book State Updating
    public Boolean updateBookState(Book book,String state){

        int rowsUpdated=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_STATE);

            stmt.setString(1, state);
            stmt.setString(2, book.getIsbn());

            rowsUpdated = stmt.executeUpdate();

        } catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

    //Book Checking
    public Boolean checkBook(Book book){

        int rowCount=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_BOOK, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1,book.getIsbn());

            ResultSet resultSet = stmt.executeQuery();

            rowCount = resultSet.last() ? resultSet.getRow() : 0;

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rowCount>0;

    }

    //Books State Updating
    public void updateBooksState(){

        try {

            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_STATE_BOOKS);

            stmt.executeUpdate();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

}

