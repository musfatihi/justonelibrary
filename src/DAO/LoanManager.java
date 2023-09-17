package DAO;

import DTO.Book;
import DTO.Borrower;
import DTO.Loan;
import UTIL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;


public class LoanManager {

    private Connection connection;

    private static final String ADD_LOAN = "insert into loans (isbn,idbrwr,days) values (?,?,?)";
    private static final String GET_LOAN = "select idbrwr,loandate,days from (select * from loans order by loandate desc) as lastloan where isbn=? limit 1";

    //private static final String DELETE_LOAN = "delete from loans where isbn=?";


    //Constructor
    public LoanManager(Connection connection) {
        this.connection = connection;
    }


    //Loan Adding
    public Boolean save(Loan loan) {

        int rowsAdded=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(ADD_LOAN);
            stmt.setString(1,loan.getBook().getIsbn());
            stmt.setInt(2, loan.getBorrower().getId());
            stmt.setInt(3, loan.getDays());

            rowsAdded = stmt.executeUpdate();


        } catch (Exception e) {

            System.out.println(e);

        }

        return rowsAdded > 0;

    }

    /*public Boolean giveBack(Book book){

        int rowsDeleted = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_LOAN);

            stmt.setString(1,book.getIsbn());

            rowsDeleted = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsDeleted>0;

    }*/


    //Getting Loans Infos
    public Loan getLoanInfos(Book book){

            try {

                PreparedStatement stmt = this.connection.prepareStatement(GET_LOAN, ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                stmt.setString(1,book.getIsbn());

                ResultSet resultSet = stmt.executeQuery();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                while (resultSet.next()) {

                    Borrower brwr;

                    try{

                        brwr = new BorrowerManager(this.connection).getBorrowerInfos(new Borrower(resultSet.getInt(1)));


                    }catch(Exception e)
                    {
                        brwr = new Borrower();
                        brwr.setId(0);
                        brwr.setName("--");
                        brwr.setCin("--");
                        brwr.setPhoneNumber("--");

                    }

                    Loan loan = new Loan(book, brwr, dateFormat.format(resultSet.getDate(2)),resultSet.getInt(3));
                    return loan;

                }

            } catch (Exception e) {

                System.out.println(e);

            }

            return null;

    }

}


