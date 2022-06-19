package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBBDD {

    public static final String DB = "Exteam";
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String USER = "apu";
    public static final String PWD = "apu";
    private String urlConnection = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;
    Connection c;

    public static void showSQLError(SQLException e) {
        System.err.println("SQL error message: " + e.getMessage());
        System.err.println("SQL state: " + e.getSQLState());
        System.err.println("SQL error code: " + e.getErrorCode());
    }

    public Connection getConexion() {
        try {
            c = DriverManager.getConnection(urlConnection, USER, PWD);
            System.out.println("BBDD: " + DB + " conectada");

        } catch (SQLException e) {
            showSQLError(e);
        }
        return c;
    }

}