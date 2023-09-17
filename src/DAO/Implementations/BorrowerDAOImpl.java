package DAO.Implementations;

import DAO.BookManager;
import DAO.BorrowerManager;
import DAO.Interfaces.BorrowerDAO;
import DAO.LoanManager;
import DTO.Book;
import DTO.Borrower;
import DTO.Loan;
import UTIL.Utilities;

import java.util.ArrayList;
import java.util.List;

public class BorrowerDAOImpl implements BorrowerDAO {

    public static BookManager bookManager;
    public static BorrowerManager borrowerManager;
    public static LoanManager loanManager;

    //Constructor-based Dependency Injection
    public BorrowerDAOImpl(BookManager bookManager, BorrowerManager borrowerManager, LoanManager loanManager){

        BookDAOImpl.bookManager = bookManager;
        BorrowerDAOImpl.borrowerManager = borrowerManager;
        BorrowerDAOImpl.loanManager = loanManager;

    }

    @Override
    //Borrower Adding
    public Borrower add(Borrower borrower) {

        return borrowerManager.addBorrower(borrower);

    }

    @Override
    //Borrower Infos Updating
    public Borrower update(Borrower borrower) {

        if(!borrowerManager.checkBorrower(borrower)){

            Utilities.displayErrorMsg("Aucun emprunteur trouvé avec cet id!!");
            return null;

        }

        if(borrowerManager.updateBorrower(borrower)){

            return borrower;

        }

        return null;

    }

    //Borrower Deletion
    public static Boolean delete(Borrower brwr, BorrowerManager borrowerManager){

        if(!borrowerManager.checkBorrower(brwr)){

            Utilities.displayErrorMsg("Aucun emprunteur avec cet id!!");
            return false;

        }

        if(borrowerManager.deleteBorrower(brwr)){
            return true;
        }

        return false;

    }

    //Show All Borrowers
    public static List<Borrower> showAll(BorrowerManager borrowerManager) {
        return borrowerManager.showAllBorrowers();
    }

    //Check Borrower
    public static Boolean check(Borrower brwr, BorrowerManager borrowerManager){

        if(!borrowerManager.checkBorrower(brwr)){

            Utilities.displayErrorMsg("Aucun emprunteur avec cet id!!");
            return false;

        }

        return true;

    }

    //Show Borrowed Books By A Specific Borrower
    public static List<Loan> showBrwds(Borrower brwr,BookManager bookManager,LoanManager loanManager) {

        List<Loan> loans = new ArrayList<>();

        List<Book> brwdBooks = bookManager.showBooksNot(Book.states.Avl.name());

        for (Book book : brwdBooks) {

            Loan loan = loanManager.getLoanInfos(book);

            if(loan.getBorrower().getId()==brwr.getId()){

                loans.add(loan);

            }

        }

        return loans;

    }


}
