package DAO;

import DTO.Book;
import DTO.Borrower;
import UTIL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BorrowerManager {

    private Connection connection;

    private static final String ADD_BORROWER = "insert into borrowers (name,cin,phonenumber) values (?,?,?)";
    private static final String UPDATE_BORROWER = "update borrowers set name = ?, cin = ?, phonenumber = ? where id=?";
    private static final String DELETE_BORROWER = "delete from borrowers where id=?";
    private static final String CHECK_BORROWER = "select * from borrowers where id=?";
    private static final String SHOW_BORROWERS = "select * from borrowers";
    private static final String GET_BORROWER = "select name,cin,phonenumber from borrowers where id=?";

    private static final String LAST_ID = "select max(id) as idbrwr from borrowers";



    //Constructor
    public BorrowerManager(Connection connection){
        this.connection = connection;
    }


    //Borrower Adding
    public Borrower addBorrower(Borrower borrower) {

        int rowsAdded=0;
        int idbrwr;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(ADD_BORROWER);
            stmt.setString(1, borrower.getName());
            stmt.setString(2, borrower.getCin());
            stmt.setString(3, borrower.getPhoneNumber());

            rowsAdded = stmt.executeUpdate();

            PreparedStatement stmnt = this.connection.prepareStatement(LAST_ID);
            ResultSet resultSet = stmnt.executeQuery();

            while (resultSet.next()) {

                idbrwr = resultSet.getInt(1);
                borrower.setId(idbrwr);
                break;

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        if (rowsAdded > 0){

            return borrower;

        }else{

            return null;

        }

    }

    //Borrower Updating
    public Boolean updateBorrower(Borrower borrower) {

        int rowsUpdated=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_BORROWER);
            stmt.setString(1, borrower.getName());
            stmt.setString(2, borrower.getCin());
            stmt.setString(3, borrower.getPhoneNumber());
            stmt.setInt(4, borrower.getId());

            rowsUpdated = stmt.executeUpdate();

        } catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated > 0;

    }

    //Borrower Deletion
    public Boolean deleteBorrower(Borrower brwr){

        int rowsDeleted = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_BORROWER);

            stmt.setInt(1,brwr.getId());

            rowsDeleted = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsDeleted>0;

    }

    //Borrower Checking
    public Boolean checkBorrower(Borrower brwr) {

        int rowCount=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_BORROWER, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1,brwr.getId());

            ResultSet resultSet = stmt.executeQuery();

            rowCount = resultSet.last() ? resultSet.getRow() : 0;

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rowCount>0;

    }

    //Show All Borrowers
    public List<Borrower> showAllBorrowers() {

        List<Borrower> borrowers = new ArrayList<Borrower>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SHOW_BORROWERS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Borrower borrower = new Borrower(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4));

                borrowers.add(borrower);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return borrowers;

    }

    //Getting Borrower Infos
    public Borrower getBorrowerInfos(Borrower brwr) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(GET_BORROWER);

            stmt.setInt(1, brwr.getId());

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                brwr.setName(resultSet.getString(1));
                brwr.setCin(resultSet.getString(2));
                brwr.setPhoneNumber(resultSet.getString(3));

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return brwr;
    }

}


