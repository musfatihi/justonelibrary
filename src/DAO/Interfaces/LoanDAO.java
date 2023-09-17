package DAO.Interfaces;

import DTO.Loan;

public interface LoanDAO {

    public Boolean save(Loan loan);


    public Boolean remove(Loan loan);

}
