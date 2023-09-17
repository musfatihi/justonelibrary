package UTIL;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

    private static final String DB_URL="jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME="postgres";
    private static final String DB_PASSWORD="1992";
    private static Connection connection;



    //Connection Establishing
    public static Connection makeConnection() {
        try
        {

            if(connection==null) {

                connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            }

            return connection;

        }
        catch ( Exception e )
        {

            System.out.println( e.getMessage( ));
            return null;

        }

    }

}

