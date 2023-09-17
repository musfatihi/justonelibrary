import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.*;

import DAO.BookManager;
import DAO.BorrowerManager;
import DAO.Implementations.BookDAOImpl;
import DAO.Implementations.BorrowerDAOImpl;
import DAO.Implementations.LoanDAOImpl;
import DAO.LoanManager;
import DTO.Book;
import DTO.Borrower;
import DTO.Loan;

import UTIL.BarChart;
import UTIL.DatabaseConnection;
import UTIL.StatsInTxtFile;
import UTIL.Utilities;


public class Main {

    private static Connection connection;
    private static BookManager bookManager;
    private static BorrowerManager borrowerManager;
    private static LoanManager loanManager;

    private static BookDAOImpl bookDAOImpl;
    private static BorrowerDAOImpl borrowerDAOImpl;
    private static LoanDAOImpl loanDAOImpl;


    static String[] options = {
            "Ajout d'un livre",
            "Modification infos Livre",
            "Suppression d'un livre",
            "Livres disponibles dans la bibliothèque",
            "Rechercher un livre par son titre ou son auteur",
            "Ajouter un emprunteur",
            "Modifier infos emprunteur",
            "Supprimer un emprunteur",
            "Les emprunteurs",
            "Verifier status d'emprunteur",
            "Emprunter un livre",
            "Retourner un livre",
            "Livres empruntés",
            "Statistiques",
    };



    public static void main(String[] args) {

        connection = DatabaseConnection.makeConnection();

        bookManager = new BookManager(connection);
        borrowerManager = new BorrowerManager(connection);
        loanManager = new LoanManager(connection);

        bookDAOImpl = new BookDAOImpl(bookManager,loanManager);
        borrowerDAOImpl = new BorrowerDAOImpl(bookManager,borrowerManager,loanManager);
        loanDAOImpl = new LoanDAOImpl(bookManager,borrowerManager,loanManager);


        while(true){

            Utilities.displayOptions(options);

            int option = Utilities.takeInput(0,options.length);

            if(option==0){
                System.out.println("A la prochaine");
                System.exit(0);
            }

            treatement(option);

        }

    }

    public static void addBook(){

        System.out.println("----------------------Ajout d'un livre--------------------------");

        //BookInfo validation
        HashMap<String, String> validatedfields = validateBookInfos(true,true);

        //Book Object creation
        Book book = new Book(validatedfields.get("isbn"),validatedfields.get("title"),validatedfields.get("author"),Book.states.Avl);

        //Book registration in DB
        showBook(bookDAOImpl.add(book));

        System.out.println("--------------------------------------------------------------------");

    }

    public static void modifyBook(){

        System.out.println("----------------------Modification d'un livre--------------------------");

        //Book Infos Validation
        HashMap<String, String> validatedfield = validateBookInfos(true,false);

        Book book = bookDAOImpl.getInfos(new Book(validatedfield.get("isbn")));

        if(book!=null){

            showBook(book);

            //Book Infos Validation
            HashMap<String, String> validatedfields = validateBookInfos(false,true);

            book.setIsbn(validatedfield.get("isbn"));
            book.setTitle(validatedfields.get("title"));
            book.setAuthor(validatedfields.get("author"));

            //Save the Modification in DB
            showBook(bookDAOImpl.update(book));

        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void deleteBook(){

        System.out.println("----------------------Suppression d'un livre--------------------------");

        //Book Infos Validation
        HashMap<String, String> validatedfields = validateBookInfos(true,false);

        //Delete Book
        if(BookDAOImpl.delete(new Book(validatedfields.get("isbn")),bookManager)){

            Utilities.displaySuccessMsg("Le livre dont l'isbn "+validatedfields.get("isbn")+" est bien supprimé");

        }else{

            Utilities.displayErrorMsg("Le livre dont l'isbn "+validatedfields.get("isbn")+" n'a pas été supprimé!!");

        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void srchBook(){

        System.out.println("----------------------Rechercher un livre--------------------------");


        HashMap<String, String> searchedField = searchBook();

        for ( String key : searchedField.keySet() ) {

            if(Objects.equals(key, "title")){

                showBooks(BookDAOImpl.searchByTitle(searchedField.get(key),bookManager),"Livres Trouvés");

            }else{

                showBooks(BookDAOImpl.searchByAuthor(searchedField.get(key),bookManager),"Livres Trouvés");

            }

        }

        System.out.println("-------------------------------------------------------------------");

    }

    public static void showBook(Book book){

        String[] columns={"ISBN","Titre", "Auteur","Etat"};

        System.out.println("----------------------Affichage infos livre--------------------------");

        if(book!=null){

            Utilities.displaySuccessMsg("Opération Réussie!!");
            List<Book> books = new ArrayList<Book>();
            books.add(book);
            Utilities.displayBooks("Livre",books,columns);

        }else{

            Utilities.displayErrorMsg("l'Opération a echoué!!");

        }

        System.out.println("-------------------------------------------------------------------");

    }

    public static void showBooks(List<Book> books,String title){

        String[] columns={"ISBN","Titre", "Auteur","Etat"};

        System.out.println("----------------------Affichage Livres--------------------------");

        Utilities.displaySuccessMsg("Opération Réussie!!");

        Utilities.displayBooks(title,books,columns);

        System.out.println("--------------------------------------------------------------");

    }

    public static HashMap<String,String> searchBook(){

        HashMap<String, String> searched = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("->1 rechercher un livre par son titre");
        System.out.println("->2 rechercher un livre par son auteur");
        int choice = Utilities.takeInput(1,2);
        switch (choice) {
            case 1:
                System.out.println("veuillez saisir le titre de livre que vous cherchez");
                try {

                    searched.put("title",reader.readLine());


                } catch (Exception e) {

                    System.out.println(e);

                }

                break;

            case 2:
                BufferedReader readerr = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("veuillez saisir l'auteur que vous cherchez");
                try {

                    searched.put("author",reader.readLine());

                } catch (Exception e) {

                    System.out.println(e);

                }

                break;

            default:
        }

        return searched;

    }



    public static void addBorrower(){

        System.out.println("----------------------Ajout d'un emprunteur--------------------------");

        //Borrower Infos validation
        HashMap<String, String> validatedfields = validateBorrowerInfos(false,true);

        //Borrower Object creation
        Borrower brw = new Borrower(validatedfields.get("name"),validatedfields.get("cin"),validatedfields.get("phoneNumber"));

        //Borrower Data DB saving
        showBorrower(borrowerDAOImpl.add(brw));

        System.out.println("--------------------------------------------------------------------");

    }

    public static void modifyBorrower(){

        System.out.println("----------------------Modification d'un emprunteur--------------------------");

        //Borrower Infos Validation
        HashMap<String, String> validatedfields = validateBorrowerInfos(true,true);

        //Borrower Object creation
        Borrower brwr = new Borrower(Integer.parseInt(validatedfields.get("id")),validatedfields.get("name"),validatedfields.get("cin"),validatedfields.get("phoneNumber"));

        //Borrower Infos DB saving
        showBorrower(borrowerDAOImpl.update(brwr));

        System.out.println("-------------------------------------------------------------------------------");

    }

    public static void deleteBorrower(){

        System.out.println("----------------------Suppression d'un emprunteur--------------------------");

        //Borrower Infos validation
        HashMap<String, String> validatedfields = validateBorrowerInfos(true,false);

        if(BorrowerDAOImpl.delete(new Borrower(Integer.parseInt(validatedfields.get("id"))),borrowerManager)){

            Utilities.displaySuccessMsg("Le membre dont l'id "+validatedfields.get("id")+" est bien supprimé");

        }else{

            Utilities.displayErrorMsg("L'Opération à echoué!!");

        }

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void showBorrowers(List<Borrower> borrowers){

        String[] columns={"ID","Nom", "CIN","N° Tél"};

        System.out.println("----------------------Affichage Emprunteurs--------------------------");

        Utilities.displaySuccessMsg("Opération Réussie!!");

        Utilities.displayBorrowers("Emprunteurs",borrowers,columns);

        System.out.println("------------------------------------------------------------------");

    }

    public static void showBorrower(Borrower borrower){

        String[] columns={"ID","Nom", "CIN","N° Tél"};

        if(borrower!=null){

            List<Borrower> brwrs = new ArrayList<Borrower>();

            brwrs.add(borrower);

            Utilities.displaySuccessMsg("Opération Réussie!!");

            Utilities.displayBorrowers("Emprunteur",brwrs,columns);

        }else{

            Utilities.displayErrorMsg("L'opération a echoué!!");

        }

    }

    public static void CheckBorrowerStatus(){

        System.out.println("----------------------Status d'un emprunteur--------------------------");

        //Borrower Infos validation
        HashMap<String, String> validatedfields = validateBorrowerInfos(true,false);

        if(BorrowerDAOImpl.check(new Borrower(Integer.parseInt(validatedfields.get("id"))),borrowerManager)){

            showLoans(BorrowerDAOImpl.showBrwds(new Borrower(Integer.parseInt(validatedfields.get("id"))),bookManager,loanManager));

        }else{

            Utilities.displayErrorMsg("L'Opération à echoué!!");

        }

        System.out.println("-----------------------------------------------------------------------------");


    }



    public static void borrowBook(){

        System.out.println("----------------------Emprunter un livre--------------------------");

        //Book Infos validation
        HashMap<String, String> validatedfields = validateBookInfos(true,false);
        Book book = new Book(validatedfields.get("isbn"));

        //Borrower Infos validation
        HashMap<String, String> validatedfieldss = validateBorrowerInfos(true,false);
        Borrower brwr = new Borrower(Integer.parseInt(validatedfieldss.get("id")));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {

            System.out.println("Pour combien de jours??");
            int days = Integer.parseInt(reader.readLine());
            Loan loan = new Loan(book,brwr,days);

            if(loanDAOImpl.save(loan)){

                Utilities.displaySuccessMsg("Opération réussie");

            }else{

                Utilities.displayErrorMsg("l'Opération a echoué!!");

            }

        }catch(Exception e){

            System.out.println(e);

        }

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void givebackBook(){

        System.out.println("----------------------Retourner un livre--------------------------");

        //Book Infos validation
        HashMap<String, String> validatedfields = validateBookInfos(true,false);

        //Book and Loan Objects Creation
        Book book = new Book(validatedfields.get("isbn"));
        Loan loan = new Loan(book);

        if(loanDAOImpl.remove(loan)){

            Utilities.displaySuccessMsg("Opération réussie");

        }else{

            Utilities.displayErrorMsg("l'Opération a échoué!!");

        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void showLoans(List<Loan> loans){

        System.out.println("----------------------Affichage Emprunts--------------------------");

        String[] columns={"ISBN","Titre","Auteur","Emprunteur","CIN","N° Tel","Date d'emprunt","Jours","Etat"};

        Utilities.displayLoans("Livres empuntés",loans,columns);

        Utilities.displaySuccessMsg("Opération Réussie!!");

        System.out.println("------------------------------------------------------------------");

    }



    public static void displayStatistics(HashMap<String,String> statistics){

        System.out.println("----------------------Statistiques--------------------------");

        new StatsInTxtFile().display(statistics);

        new BarChart("Statistiques").display(statistics);

        Utilities.displaySuccessMsg("Les statistiques ont été bien enregistrées dans le fichier!!");

        System.out.println("-----------------------------------------------------------");

    }



    public static void treatement(int option){

        switch(option) {
            case 1:
                addBook();
                break;
            case 2:
                modifyBook();
                break;
            case 3:
                deleteBook();
                break;
            case 4:
                showBooks(BookDAOImpl.showAll(bookManager),"Livres Disponibles");
                break;
            case 5:
                srchBook();
                break;
            case 6:
                addBorrower();
                break;
            case 7:
                modifyBorrower();
                break;
            case 8:
                deleteBorrower();
                break;
            case 9:
                showBorrowers(BorrowerDAOImpl.showAll(borrowerManager));
                break;
            case 10:
                CheckBorrowerStatus();
                break;
            case 11:
                borrowBook();
                break;
            case 12:
                givebackBook();
                break;
            case 13:
                showLoans(BookDAOImpl.showBrwds(bookManager,loanManager));
                break;
            case 14:
                displayStatistics(BookDAOImpl.getStatistics(bookManager));
                break;
            default:
                break;
        }

    }

    public static HashMap<String,String> validateBookInfos(Boolean isisbn, Boolean allR){

        HashMap<String, String> fields = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String isbn;

        try {
                if(isisbn){

                    do{

                        System.out.println("Entrez l'ISBN : ");
                        isbn = reader.readLine();

                    }while(!Utilities.isNumber(isbn) || !Utilities.isIsbnLengthValid(isbn));
                    fields.put("isbn",isbn);

                }

                if(allR){

                    System.out.println("Entrez le titre : ");
                    fields.put("title",reader.readLine());

                    System.out.println("Entrez l'auteur : ");
                    fields.put("author",reader.readLine());

                }

        }
        catch(Exception e){
            System.out.println(e);
        }

        return fields;

    }

    public static HashMap<String,String> validateBorrowerInfos(Boolean isId,Boolean isR){

        HashMap<String, String> fields = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String id;

        try {

                if(isId){

                    do {

                        System.out.println("Entrez l'id de membre: ");
                        id = reader.readLine();

                    } while (!Utilities.isNumber(id));

                    fields.put("id", id);

                }


                if(isR) {

                    System.out.println("Entrez le nom: ");
                    fields.put("name", reader.readLine());

                    System.out.println("Entrez le CIN: ");
                    fields.put("cin", reader.readLine());

                    System.out.println("Entrez le N° de tél: ");
                    fields.put("phoneNumber", reader.readLine());

                }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return fields;

    }

}
