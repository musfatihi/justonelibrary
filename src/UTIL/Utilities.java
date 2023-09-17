package UTIL;

import DTO.Book;
import DTO.Borrower;
import DTO.Loan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Utilities {

    // Asking for Input as Choice
    public static int takeInput(int min, int max) {
        String choice;
        Scanner input = new Scanner(System.in);

        while(true)
        {
            System.out.println("Faites un choix : ");

            choice = input.next();

            if(Utilities.isNumber(choice) && Integer.parseInt(choice) >= min && Integer.parseInt(choice) <= max)
            {
                return Integer.parseInt(choice);
            }
            else
            {
                Utilities.displayErrorMsg("Entrée non valide.");

            }

        }

    }

    //Display all options to choose from
    public static void displayOptions(String[] options){

        System.out.println("--------------------------------------------------");

        for (int i = 0; i < options.length; i++) {
            System.out.println(i+1+" "+options[i]);
            if(i==4 || i==9 || i==12){
                System.out.println("--------------------------------------------------");
            }
            if(i==13){
                System.out.println("--------------------------------------------------");
            }
        }

        System.out.println("0 Quitter");
        System.out.println("--------------------------------------------------");


    }

    //Check if Input is A Number
    public static Boolean isNumber(String str){

        try {

            for (int i = 0; i < str.length();i++) {

                Integer.parseInt(str.substring(i,i+1));

            }

        }
        catch(NumberFormatException e){

            displayErrorMsg("Not A Number!!");

            return false;

        }

        return true;

    }

    //Check if ISBN Length is Valid
    public static Boolean isIsbnLengthValid(String isbn){

        if(isbn.length() == 10 || isbn.length() == 13)
        {
            return true;
        }

        Utilities.displayErrorMsg("La longeur n'est pas valide!!(10 ou 13)");

        return false;

    }

    //Display Success Message
    public static void displaySuccessMsg(String msg){
        System.out.print("\u001B[32m");
        System.out.println(msg);
        System.out.print("\u001B[0m");
    }

    //Display Error Message
    public static void displayErrorMsg(String msg){
        System.out.print("\u001B[31m");
        System.out.println(msg);
        System.out.print("\u001B[0m");
    }


    public static void displayBooks(String title, List<Book> books, String[] columns){

        JFrame frame = new JFrame(title);

        frame.setSize(400, 300);

        // Create a DefaultTableModel to store data for the table
        DefaultTableModel model = new DefaultTableModel();

        // Add columns to the table model
        for (int i = 0; i < columns.length; i++) {
            model.addColumn(columns[i]);
        }


        if(!books.isEmpty()){

            String [] bookInfos = new String[columns.length];

            for (int j = 0; j < books.size(); j++) {

                Book book = books.get(j);

                bookInfos[0]=book.getIsbn();
                bookInfos[1]=book.getTitle();
                bookInfos[2]=book.getAuthor();
                bookInfos[3]=book.getState().toString();

                model.addRow(bookInfos);

            }

        }

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);

        // Add the JTable to a JScrollPane (optional, for scrollable tables)
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the JFrame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display the JFrame
        frame.setVisible(true);

    }


    public static void displayLoans(String title, List<Loan> loans, String[] columns){

        JFrame frame = new JFrame(title);

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a DefaultTableModel to store data for the table
        DefaultTableModel model = new DefaultTableModel();

        // Add columns to the table model
        for (int i = 0; i < columns.length; i++) {
            model.addColumn(columns[i]);
        }

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);

        if(!loans.isEmpty()){

            String [] loanInfos = new String[columns.length];

            for (int j = 0; j < loans.size(); j++) {

                Loan loan = loans.get(j);

                loanInfos[0]=loan.getBook().getIsbn();
                loanInfos[1]=loan.getBook().getTitle();
                loanInfos[2]=loan.getBook().getAuthor();
                loanInfos[3]=loan.getBorrower().getName();
                loanInfos[4]=loan.getBorrower().getCin();
                loanInfos[5]=loan.getBorrower().getPhoneNumber();
                loanInfos[6]=loan.getLoanDate();
                loanInfos[7]=String.valueOf(loan.getDays());
                loanInfos[8]=loan.getBook().getState().name();

                model.addRow(loanInfos);

            }

        }

        // Add the JTable to a JScrollPane (optional, for scrollable tables)
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the JFrame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display the JFrame
        frame.setVisible(true);

    }

    public static void displayBorrowers(String title, List<Borrower> brwrs, String[] columns){

        String [] brwrInfos = new String[columns.length];

        JFrame frame = new JFrame(title);

        frame.setSize(400, 300);

        // Create a DefaultTableModel to store data for the table
        DefaultTableModel model = new DefaultTableModel();

        // Add columns to the table model
        for (int i = 0; i < columns.length; i++) {
            model.addColumn(columns[i]);
        }

        if(!brwrs.isEmpty()){

            for (int j = 0; j < brwrs.size(); j++) {

                Borrower brwr = brwrs.get(j);

                brwrInfos[0]=(brwr.getId()>0)?String.valueOf(brwr.getId()):"--";
                brwrInfos[1]=brwr.getName();
                brwrInfos[2]=brwr.getCin();
                brwrInfos[3]=brwr.getPhoneNumber();

                model.addRow(brwrInfos);

            }

        }

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);

        // Add the JTable to a JScrollPane (optional, for scrollable tables)
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the JFrame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display the JFrame
        frame.setVisible(true);

    }

}


