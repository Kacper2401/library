package sample.users;

import sample.Connect;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account extends Connect {

    public void addAccount(String login, String password, int accountLevel) {
        Connection dbh = getConnect();
        String query;

        query = "INSERT INTO accounts" +
                "            (login," +
                "             password," +
                "             access_level) " +
                "VALUES      (?," +
                "             ?," +
                "             ?)  ";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString (1, login);
            preparedQuery.setString (2, hashPassword(password));
            preparedQuery.setInt (3, accountLevel);

            preparedQuery.execute();
        }catch (SQLException error) {
            System.out.println("Error with adding account: " + error.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    public int logIn(String login, String password) {
        Connection dbh = getConnect();
        ResultSet rs;
        String query;

        query = "SELECT access_level " +
                "FROM   accounts " +
                "WHERE  login = ? " +
                "       AND password = ?";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString (1, login);
            preparedQuery.setString (2, hashPassword(password));

            rs = preparedQuery.executeQuery();

            rs.last();
            if(rs.getRow() == 1) {
                return rs.getInt(1);
            }

        }catch (SQLException error) {
            System.out.println("Error with Login: " + error.getMessage());
        }finally {
            closeConnection(dbh);
        }

        return -1;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            StringBuilder hashText;

            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            hashText = new StringBuilder(no.toString(16));

            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        }catch (NoSuchAlgorithmException error) {
            System.out.println("Error with Algorithm: " + error.getMessage());
        }

        return null;
    }
}
