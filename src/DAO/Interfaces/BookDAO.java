package DAO.Interfaces;


import DTO.Book;

public interface BookDAO {

    public Book add(Book book);

    public Book update(Book book);

    public Book getInfos(Book book);

}

