package DAO.Implementations;

import DAO.BookManager;
import DAO.BorrowerManager;
import DAO.Interfaces.LoanDAO;
import DAO.LoanManager;
import DTO.Book;
import DTO.Loan;
import UTIL.Utilities;

public class LoanDAOImpl implements LoanDAO {

    private static LoanManager loanManager;

    private static BookManager bookManager;

    private static BorrowerManager borrowerManager;

    public LoanDAOImpl(BookManager bookManager,BorrowerManager borrowerManager,LoanManager loanManager){

        LoanDAOImpl.bookManager = bookManager;

        LoanDAOImpl.loanManager = loanManager;

        LoanDAOImpl.borrowerManager = borrowerManager;

    }

    @Override
    public Boolean save(Loan loan) {
        Boolean Errors=false;

        if(!bookManager.checkBookState(loan.getBook(),"Avl")){
            Utilities.displayErrorMsg("Livre non disponible!!");
            Errors=true;
        }

        if(!borrowerManager.checkBorrower(loan.getBorrower())){
            Utilities.displayErrorMsg("Membre non inscrit!!");
            Errors=true;
        }

        if(Errors){
            return false;
        }

        if(loanManager.save(loan)) {

            //new BookManager().updateState(loan.getBook(),"NotAvl");

            return true;
        }

        return false;
    }



    @Override
    public Boolean remove(Loan loan) {

        if(!bookManager.checkBook(loan.getBook())){

            Utilities.displayErrorMsg("Ce livre n'est pas enregistré!!");

            return false;

        }

        if(bookManager.checkBookState(loan.getBook(),Book.states.Avl.name())){

            Utilities.displayErrorMsg("Le livre est disponible deja!!");

            return false;

        }

        if(bookManager.updateBookState(loan.getBook(), Book.states.Avl.name())) {

            return true;

        }

        return false;

    }

}
