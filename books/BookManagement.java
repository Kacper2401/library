package sample.books;

import sample.Connect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BookManagement extends Connect {

    public void addBook(String title, String author, String genre, int quantity) {
        Connection dbh = getConnect();
        ResultSet rs;
        String query;

        query = "SELECT id " +
                "FROM   books_list " +
                "WHERE  title = ? " +
                "       AND genre = ? " +
                "       AND author = ? ";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString(1, title);
            preparedQuery.setString(2, genre);
            preparedQuery.setString(3, author);

            rs = preparedQuery.executeQuery();

            rs.last();
            if(rs.getRow() == 1) {
                updateQuantity(dbh, rs.getInt(1), quantity);
            }else {
                insertNewBook(dbh, title, author, genre, quantity);
            }
        }catch (SQLException throwable) {
            System.out.println("Can't add book, error: " + throwable.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    public void deleteBook(int bookId, int quantity) {
        Connection dbh = getConnect();
        String query;

        query = "UPDATE books_list " +
                "SET quantity = quantity - ? " +
                "WHERE ID = ?";

        try{
            if(quantity == 0 || (checkQuantityBooks(dbh, bookId) - quantity ) < 0) {
                deleteAllBooks(dbh, bookId);
            }else {
                PreparedStatement preparedQuery = dbh.prepareStatement(query);

                preparedQuery.setInt(1, quantity);
                preparedQuery.setInt(2, bookId);

                preparedQuery.execute();
            }
        }catch (SQLException throwable) {
            System.out.println("Can't delete book, error: " + throwable.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    public void rentBook(int bookId, String user, String title, String author) throws BookException.BookNotAvailable, BookException.BookAlreadyRented {
        Connection dbh = getConnect();
        String query;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        query = "INSERT INTO history" +
                "            (rent_date," +
                "             account_id," +
                "             book_id) "+
                "VALUES      (?," +
                "             ?," +
                "             ?)";

        try{
            if(bookId == -1) {
                bookId = checkIfBookId(dbh, title, author);
            }

            if (checkIfBookExist(dbh, bookId)) {
                if(!checkIfBookHasBeenRented(dbh, bookId, checkUserId(dbh, user))) {
                    PreparedStatement preparedQuery = dbh.prepareStatement(query);

                    preparedQuery.setTimestamp(1, Timestamp.valueOf(formatter.format(date)));
                    preparedQuery.setInt(2, checkUserId(dbh, user));
                    preparedQuery.setInt(3, bookId);

                    preparedQuery.execute();

                    updateQuantity(dbh, bookId, -1);
                }else {
                    throw new BookException.BookAlreadyRented();
                }
            }else {
                throw new BookException.BookNotAvailable();
            }
        }catch (SQLException throwable) {
            System.out.println("Can't rent book, error: " + throwable.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    public void returnBook(int bookId, String user, String title, String author) throws BookException.BookNotRented {
        Connection dbh = getConnect();
        String query;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        query = "UPDATE history " +
                "SET    return_date = ? "+
                "WHERE  account_id = ? " +
                "       AND book_id = ? " +
                "       AND return_date IS NULL";

        try{
            if(bookId == -1) {
                bookId = checkIfBookId(dbh, title, author);
            }

            if(!checkForRentedBook(dbh, checkUserId(dbh, user), bookId)) {
                throw new BookException.BookNotRented();
            }

            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setTimestamp(1, Timestamp.valueOf(formatter.format(date)));
            preparedQuery.setInt(2, checkUserId(dbh, user));
            preparedQuery.setInt(3, bookId);

            preparedQuery.execute();

            updateQuantity(dbh, bookId, 1);
        }catch (SQLException throwable) {
            System.out.println("Can't return book, error: " + throwable.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    public ResultSet allBooksDetails(){
        Connection dbh = getConnect();
        String query;

        query = "SELECT id, " +
                "       title," +
                "       genre, " +
                "       author, " +
                "       quantity " +
                "FROM   books_list";
        try{
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            return preparedQuery.executeQuery();
        }catch (SQLException throwable) {
            System.out.println("Can't load book list, error: " + throwable.getMessage());
        }

        return null;
    }

    public ResultSet userBorrowBookList(String user) {
        Connection dbh = getConnect();
        String query;

        query = "SELECT books_list.title, " +
                "       books_list.genre, " +
                "       books_list.author, " +
                "       history.rent_date " +
                "FROM   books_list " +
                "       INNER JOIN history " +
                "                  ON books_list.id = history.book_id " +
                "WHERE  account_id = ?" +
                "       AND history.return_date IS NULL";

        try{
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setInt(1, checkUserId(dbh, user));

            return preparedQuery.executeQuery();

        }catch (SQLException throwable) {
            System.out.println("Can't load user book list, error: " + throwable.getMessage());
        }

        return null;
    }

    private void deleteAllBooks(Connection dbh, int bookId) throws SQLException{
        String query;

        query = "DELETE FROM books_list " +
                "WHERE id = ?";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);
        preparedQuery.setInt(1, bookId);

        preparedQuery.execute();
    }

    private int checkQuantityBooks(Connection dbh, int bookId) throws SQLException {
        ResultSet rs;
        String query;

        query = "SELECT quantity " +
                "FROM   books_list " +
                "WHERE  id = ?";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setInt(1, bookId);

        rs = preparedQuery.executeQuery();

        rs.last();
        return rs.getInt(1);
    }

    private void updateQuantity(Connection dbh, int bookId, int quantity) throws SQLException {
        String query;

        query = "UPDATE books_list " +
                "SET    quantity = quantity + ? " +
                "WHERE  id = ? ";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setInt(1, quantity);
        preparedQuery.setInt(2, bookId);

        preparedQuery.execute();
    }

    private void insertNewBook(Connection dbh, String title, String author, String genre, int quantity) throws SQLException {
        String query;

        query = "INSERT INTO books_list" +
                "            (title," +
                "             genre," +
                "             author," +
                "             quantity) " +
                "VALUES      (?," +
                "             ?," +
                "             ?," +
                "             ?)";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setString(1, title);
        preparedQuery.setString(2, genre);
        preparedQuery.setString(3, author);
        preparedQuery.setInt(4, quantity);

        preparedQuery.execute();
    }

    private boolean checkIfBookExist(Connection dbh, int bookId) throws SQLException {
        String query;
        ResultSet rs;

        query = "SELECT title " +
                "FROM   books_list " +
                "WHERE  id = ?" +
                "       AND quantity > 0";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setInt(1, bookId);

        rs = preparedQuery.executeQuery();

        rs.last();

        return rs.getRow() == 1;
    }

    private int checkIfBookId(Connection dbh, String title, String author) throws SQLException {
        String query;
        ResultSet rs;

        query = "SELECT id " +
                "FROM   books_list " +
                "WHERE  title = ? " +
                "       AND author = ?";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setString(1, title);
        preparedQuery.setString(2, author);

        rs = preparedQuery.executeQuery();

        rs.last();

        if(rs.getRow() == 1) {
            return rs.getInt(1);
        }else {
            return -1;
        }
    }

    private int checkUserId(Connection dbh, String user) throws SQLException {
        String query;
        ResultSet rs;

        query = "SELECT id " +
                "FROM   accounts " +
                "WHERE  login = ?";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setString(1, user);

        rs = preparedQuery.executeQuery();

        rs.last();

        return rs.getInt(1);
    }

    private boolean checkIfBookHasBeenRented(Connection dbh, int bookId, int userId) throws SQLException {
        String query;
        ResultSet rs;

        query = "SELECT id " +
                "FROM   history " +
                "WHERE  book_id = ?" +
                "       AND account_id = ?" +
                "       AND return_date IS NULL";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setInt(1, bookId);
        preparedQuery.setInt(2, userId);

        rs = preparedQuery.executeQuery();

        rs.last();

        return rs.getRow() == 1;
    }

    private boolean checkForRentedBook(Connection dbh, int userId, int bookId) throws SQLException {
        String query;
        ResultSet rs;

        query = "SELECT ID " +
                "FROM   history " +
                "WHERE  account_id = ? " +
                "       AND book_id = ? " +
                "       AND return_date IS NULL";

        PreparedStatement preparedQuery = dbh.prepareStatement(query);

        preparedQuery.setInt(1, userId);
        preparedQuery.setInt(2, bookId);

        rs = preparedQuery.executeQuery();

        rs.last();

        return rs.getRow() == 1;
    }
}