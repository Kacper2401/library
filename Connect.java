package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final String HOST = "localhost";
    private final String BASE = "library";
    private final String PORT = "3306";
    private final String ULR =  "jdbc:mysql://" + HOST + ":" + PORT+ "/" + BASE;
    private final String USER = "root";
    private final String PASSWORD = "";

    public Connection getConnect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(ULR, USER, PASSWORD);
        }catch (SQLException error) {
            System.out.println("Server connection error: " + error.getMessage());
        }

        return conn;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException error) {
            System.out.println("Can't close connection" + error.getMessage());
        }
    }
}
