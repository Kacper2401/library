package sample.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.books.BookException;
import sample.books.BookManagement;
import sample.users.Account;
import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneratorViews extends BookManagement {
    private final Stage windows;
    private String userName;
    private int userLevel;

    public GeneratorViews(Stage primaryStage){
        windows = primaryStage;
    }

    public Scene getStartView() {
        Group view = new Group();
        Button buttonLogin = new Button("Log in");
        TextField fieldLogin = new TextField();
        PasswordField fieldPassword = new PasswordField();
        Text messageLogin = new Text();
        Text messagePassword = new Text();
        Text messageWelcome = new Text();

        messageWelcome.setText("International Library System");
        messageWelcome.setLayoutX(60);
        messageWelcome.setLayoutY(70);
        messageWelcome.setStyle("-fx-font: 24 arial");
        messageWelcome.setFill(Color.WHITE);

        messageLogin.setText("Login");
        messageLogin.setLayoutX(70);
        messageLogin.setLayoutY(155);
        messageLogin.setFill(Color.WHITE);
        messageLogin.setStyle("-fx-font: 20 arial");

        fieldLogin.setLayoutX(180);
        fieldLogin.setLayoutY(135);

        messagePassword.setText("Password");
        messagePassword.setLayoutX(70);
        messagePassword.setLayoutY(205);
        messagePassword.setFill(Color.WHITE);
        messagePassword.setStyle("-fx-font: 20 arial");

        fieldPassword.setLayoutX(180);
        fieldPassword.setLayoutY(185);

        buttonLogin.setLayoutX(150);
        buttonLogin.setLayoutY(250);
        buttonLogin.setPrefWidth(75);

        addLogInEventToButton(view, buttonLogin, fieldLogin, fieldPassword);

        view.getChildren().addAll(buttonLogin, fieldLogin,
                fieldPassword, messageLogin,
                messagePassword, messageWelcome);

        return new Scene(view, 400, 400, Color.rgb(100, 5, 95));
    }

    private Scene getAccountCockpitView() {
        Group view = new Group();
        Text messageUserName = new Text();
        Button buttonAddBook = new Button("Add books");
        Button buttonDeleteBook = new Button("Delete book");
        Button buttonDetailsBooks = new Button("Book index");
        Button buttonRentBook = new Button("Rent book");
        Button buttonReturnBook = new Button("Return book");
        Button buttonMyBooks = new Button("My books");

        buttonRentBook.setLayoutX(20);
        buttonRentBook.setLayoutY(70);
        buttonRentBook.setPrefWidth(150);
        buttonRentBook.setPrefHeight(40);

        buttonReturnBook.setLayoutX(220);
        buttonReturnBook.setLayoutY(70);
        buttonReturnBook.setPrefWidth(150);
        buttonReturnBook.setPrefHeight(40);

        buttonDetailsBooks.setLayoutX(20);
        buttonDetailsBooks.setLayoutY(170);
        buttonDetailsBooks.setPrefWidth(150);
        buttonDetailsBooks.setPrefHeight(40);

        buttonMyBooks.setLayoutX(220);
        buttonMyBooks.setLayoutY(170);
        buttonMyBooks.setPrefWidth(150);
        buttonMyBooks.setPrefHeight(40);

        messageUserName.setText("User: " + this.userName);
        messageUserName.setLayoutX(145);
        messageUserName.setLayoutY(40);
        messageUserName.setStyle("-fx-font: 20 arial");
        messageUserName.setFill(Color.WHITE);

        view.getChildren().addAll(buttonRentBook, buttonReturnBook,
                                    buttonMyBooks, buttonDetailsBooks,
                                    messageUserName);

        if(this.userLevel == 1) {
            buttonAddBook.setLayoutX(20);
            buttonAddBook.setLayoutY(270);
            buttonAddBook.setPrefWidth(150);
            buttonAddBook.setPrefHeight(40);

            buttonDeleteBook.setLayoutX(220);
            buttonDeleteBook.setLayoutY(270);
            buttonDeleteBook.setPrefWidth(150);
            buttonDeleteBook.setPrefHeight(40);

            view.getChildren().addAll(buttonAddBook, buttonDeleteBook);
        }

        eventGetAddBookView(buttonAddBook);
        eventGetDeleteBookView(buttonDeleteBook);
        eventGetMyBookListView(buttonMyBooks);
        eventGetBookIndexView(buttonDetailsBooks);
        eventGetReturnBookView(buttonReturnBook);
        eventGetRentBookView(buttonRentBook);

        return new Scene(view, 400, 400, Color.rgb(100, 5, 95));
    }

    private Scene getAddBookView() {
        Group view = new Group();
        Text messageTitle = new Text();
        Text messageAuthor = new Text();
        Text messageGenre = new Text();
        Text messageQuantity = new Text();
        TextField fieldTitle = new TextField();
        TextField fieldAuthor = new TextField();
        TextField fieldGenre = new TextField();
        TextField fieldQuantity = new TextField();
        Button buttonAddBook = new Button("Add books");
        Button buttonReturn = new Button("Return");

        messageTitle.setLayoutX(50);
        messageTitle.setLayoutY(40);
        messageTitle.setStyle("-fx-font: 20 arial");
        messageTitle.setFill(Color.WHITE);
        messageTitle.setText("Title");

        fieldTitle.setLayoutX(50);
        fieldTitle.setLayoutY(55);
        fieldTitle.setPrefWidth(200);

        messageAuthor.setLayoutX(50);
        messageAuthor.setLayoutY(120);
        messageAuthor.setStyle("-fx-font: 20 arial");
        messageAuthor.setFill(Color.WHITE);
        messageAuthor.setText("Author");

        fieldAuthor.setLayoutX(50);
        fieldAuthor.setLayoutY(135);
        fieldAuthor.setPrefWidth(200);

        messageGenre.setLayoutX(50);
        messageGenre.setLayoutY(200);
        messageGenre.setStyle("-fx-font: 20 arial");
        messageGenre.setFill(Color.WHITE);
        messageGenre.setText("Genre");

        fieldGenre.setLayoutX(50);
        fieldGenre.setLayoutY(215);
        fieldGenre.setPrefWidth(200);

        messageQuantity.setLayoutX(50);
        messageQuantity.setLayoutY(280);
        messageQuantity.setStyle("-fx-font: 20 arial");
        messageQuantity.setFill(Color.WHITE);
        messageQuantity.setText("Quantity");

        fieldQuantity.setLayoutX(50);
        fieldQuantity.setLayoutY(295);
        fieldQuantity.setPrefWidth(200);

        buttonAddBook.setLayoutX(50);
        buttonAddBook.setLayoutY(350);

        buttonReturn.setLayoutX(150);
        buttonReturn.setLayoutY(350);

        view.getChildren().addAll(fieldTitle, fieldAuthor,
                                    fieldGenre, fieldQuantity,
                                    buttonAddBook, buttonReturn,
                                    messageTitle, messageAuthor,
                                    messageGenre, messageQuantity);

        eventGetMenuView(buttonReturn);
        eventAddBook(view, buttonAddBook, fieldTitle, fieldAuthor, fieldGenre, fieldQuantity);

        return new Scene(view, 400, 400, Color.rgb(100, 5, 95));
    }

    private Scene getDeleteBookView() {
        Group view = new Group();
        Text messageId = new Text();
        Text messageQuantity = new Text();
        TextField fieldId = new TextField();
        TextField fieldQuantity = new TextField();
        Button buttonDeleteBook = new Button("Delete books");
        Button buttonReturn = new Button("Return");

        messageId.setText("ID");
        messageId.setLayoutY(40);
        messageId.setLayoutX(180);
        messageId.setStyle("-fx-font: 24 arial");
        messageId.setFill(Color.WHITE);

        fieldId.setLayoutX(100);
        fieldId.setLayoutY(50);
        fieldId.setPrefWidth(200);

        messageQuantity.setText("Quantity");
        messageQuantity.setLayoutX(150);
        messageQuantity.setLayoutY(120);
        messageQuantity.setStyle("-fx-font: 24 arial");
        messageQuantity.setFill(Color.WHITE);

        fieldQuantity.setLayoutX(100);
        fieldQuantity.setLayoutY(130);
        fieldQuantity.setPrefWidth(200);

        view.getChildren().addAll(messageId, messageQuantity,
                                    fieldId, fieldQuantity,
                                    buttonDeleteBook, buttonReturn);

        buttonReturn.setLayoutX(100);
        buttonReturn.setLayoutY(200);

        buttonDeleteBook.setLayoutX(220);
        buttonDeleteBook.setLayoutY(200);

        eventGetMenuView(buttonReturn);
        eventDeleteBook(view, buttonDeleteBook, fieldId, fieldQuantity);

        return new Scene(view, 400, 400, Color.rgb(100, 5, 95));
    }

    private Scene getMyBooksView() {
        Group view = new Group();
        TableView<Book> table;
        Button buttonReturn = new Button("Return");

        TableColumn<Book, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setMinWidth(250);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> columnGenre = new TableColumn<>("Genre");
        columnGenre.setMinWidth(250);
        columnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Book, String> columnAuthor = new TableColumn<>("Author");
        columnAuthor.setMinWidth(250);
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> columnRentDate = new TableColumn<>("Rent Date");
        columnRentDate.setMinWidth(250);
        columnRentDate.setCellValueFactory(new PropertyValueFactory<>("rentDate"));

        table = new TableView<>();
        table.setItems(getUserBookList(userBorrowBookList(this.userName)));
        table.getColumns().addAll(columnTitle, columnGenre, columnAuthor, columnRentDate);

        buttonReturn.setLayoutX(450);
        buttonReturn.setLayoutY(450);
        buttonReturn.setMinWidth(100);

        eventGetMenuView(buttonReturn);

        view.getChildren().addAll(buttonReturn, table);

        return new Scene(view, 1000, 500, Color.rgb(100, 5, 95));
    }

    private Scene getBookIndexView() {
        Group view = new Group();
        Button buttonReturn = new Button("Return");
        TableView<Book> table;

        buttonReturn.setLayoutX(450);
        buttonReturn.setLayoutY(450);
        buttonReturn.setMinWidth(100);

        TableColumn<Book, String> columnId = new TableColumn<>("Id");
        columnId.setMinWidth(200);
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setMinWidth(200);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> columnGenre = new TableColumn<>("Genre");
        columnGenre.setMinWidth(200);
        columnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Book, String> columnAuthor = new TableColumn<>("Author");
        columnAuthor.setMinWidth(200);
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> columnQuantity = new TableColumn<>("Quantity");
        columnQuantity.setMinWidth(200);
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table = new TableView<>();
        table.setItems(getBookList(allBooksDetails()));
        table.getColumns().addAll(columnId, columnTitle, columnGenre, columnAuthor, columnQuantity);

        eventGetMenuView(buttonReturn);

        view.getChildren().addAll(buttonReturn, table);

        return new Scene(view, 1000, 500, Color.rgb(100, 5, 95));
    }

    private Scene getRentBookView() {
        Group view = new Group();
        Button buttonRentBook = new Button("Rent book");
        Button buttonReturn = new Button("Return");
        Text messageTitle = new Text();
        Text messageAuthor = new Text();
        Text messageId = new Text();
        TextField fieldTitle = new TextField();
        TextField fieldAuthor = new TextField();
        TextField fieldId = new TextField();

        messageTitle.setText("Title");
        messageTitle.setLayoutX(175);
        messageTitle.setLayoutY(50);
        messageTitle.setStyle("-fx-font: 24 arial");
        messageTitle.setFill(Color.WHITE);

        fieldTitle.setLayoutX(100);
        fieldTitle.setLayoutY(60);
        fieldTitle.setPrefWidth(200);

        messageAuthor.setText("Author");
        messageAuthor.setLayoutX(165);
        messageAuthor.setLayoutY(120);
        messageAuthor.setStyle("-fx-font: 24 arial");
        messageAuthor.setFill(Color.WHITE);

        fieldAuthor.setLayoutX(100);
        fieldAuthor.setLayoutY(130);
        fieldAuthor.setPrefWidth(200);

        messageId.setText("ID");
        messageId.setLayoutX(190);
        messageId.setLayoutY(190);
        messageId.setStyle("-fx-font: 24 arial");
        messageId.setFill(Color.WHITE);

        fieldId.setLayoutX(100);
        fieldId.setLayoutY(200);
        fieldId.setPrefWidth(200);

        buttonRentBook.setLayoutX(225);
        buttonRentBook.setLayoutY(250);
        buttonRentBook.setPrefWidth(75);

        buttonReturn.setLayoutX(100);
        buttonReturn.setLayoutY(250);
        buttonReturn.setPrefWidth(75);

        view.getChildren().addAll(buttonRentBook, buttonReturn, messageTitle,
                                    messageAuthor, messageId, fieldTitle,
                                    fieldAuthor, fieldId);

        eventRentBook(view, buttonRentBook, fieldId, fieldTitle, fieldAuthor);
        eventGetMenuView(buttonReturn);

        return new Scene(view, 400, 400, Color.rgb(100, 5, 95));
    }

    private Scene getReturnBookView() {
        Group view = new Group();
        Button buttonReturnBook = new Button("Return book");
        Button buttonReturn = new Button("Return");
        Text messageTitle = new Text();
        Text messageAuthor = new Text();
        Text messageId = new Text();
        TextField fieldTitle = new TextField();
        TextField fieldAuthor = new TextField();
        TextField fieldId = new TextField();

        messageTitle.setText("Title");
        messageTitle.setLayoutX(175);
        messageTitle.setLayoutY(50);
        messageTitle.setStyle("-fx-font: 24 arial");
        messageTitle.setFill(Color.WHITE);

        fieldTitle.setLayoutX(100);
        fieldTitle.setLayoutY(60);
        fieldTitle.setPrefWidth(200);

        messageAuthor.setText("Author");
        messageAuthor.setLayoutX(165);
        messageAuthor.setLayoutY(120);
        messageAuthor.setStyle("-fx-font: 24 arial");
        messageAuthor.setFill(Color.WHITE);

        fieldAuthor.setLayoutX(100);
        fieldAuthor.setLayoutY(130);
        fieldAuthor.setPrefWidth(200);

        messageId.setText("ID");
        messageId.setLayoutX(190);
        messageId.setLayoutY(190);
        messageId.setStyle("-fx-font: 24 arial");
        messageId.setFill(Color.WHITE);

        fieldId.setLayoutX(100);
        fieldId.setLayoutY(200);
        fieldId.setPrefWidth(200);

        buttonReturnBook.setLayoutX(210);
        buttonReturnBook.setLayoutY(250);
        buttonReturnBook.setPrefWidth(90);

        buttonReturn.setLayoutX(100);
        buttonReturn.setLayoutY(250);
        buttonReturn.setPrefWidth(90);

        view.getChildren().addAll(buttonReturnBook, buttonReturn, messageTitle,
                                    messageAuthor, messageId, fieldTitle,
                                    fieldAuthor, fieldId);

        eventGetMenuView(buttonReturn);
        eventReturnBook(view, buttonReturnBook, fieldId, fieldTitle, fieldAuthor);

        return new Scene(view, 400, 400, Color.rgb(100, 5, 95));
    }

    private ObservableList<Book> getUserBookList(ResultSet bookList) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            while(bookList.next()) {
                books.add(new UserBookList(bookList.getString(1), bookList.getString(2), bookList.getString(3), bookList.getTimestamp(4).toString().substring(0,19)));
            }
        } catch (SQLException throwable) {
            System.out.println("Problem downloading data to table: " + throwable.getMessage());
        }

        return books;
    }

    private ObservableList<Book> getBookList(ResultSet bookList) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            while(bookList.next()) {
                books.add(new BookList(String.valueOf(bookList.getInt(1)), bookList.getString(2), bookList.getString(3), bookList.getString(4), String.valueOf(bookList.getInt(5))));
            }
        } catch (SQLException throwable) {
            System.out.println("Problem downloading data to table: " + throwable.getMessage());
        }

        return books;
    }

    private void addLogInEventToButton(Group view, Button button, TextField login, TextField password) {
        Account account = new Account();

        EventHandler<ActionEvent> logIn = event -> {
            int userLevel = account.logIn(login.getText(), password.getText());

            if(userLevel != -1) {
                this.userName = login.getText();
                this.userLevel = userLevel;

                windows.setScene(getAccountCockpitView());
            }else {
                view.getChildren().remove(view.lookup("#wrongData"));
                Text wrongData = new Text();

                wrongData.setText("Zły users/ hasło");
                wrongData.setLayoutX(120);
                wrongData.setLayoutY(350);
                wrongData.setStyle("-fx-font: 24 arial");
                wrongData.setFill(Color.WHITE);
                wrongData.setId("wrongData");

                view.getChildren().add(wrongData);
            }
        };

        button.setOnAction(logIn);
    }

    private void eventGetAddBookView(Button button) {
        EventHandler<ActionEvent> addBookView = event -> windows.setScene(getAddBookView());

        button.setOnAction(addBookView);
    }

    private void eventAddBook(Group view, Button button, TextField fieldTitle, TextField fieldAuthor, TextField fieldGenre, TextField fieldQuantity) {
        EventHandler<ActionEvent> addBook = event -> {
            view.getChildren().remove(view.lookup("#added"));
            Text addedMessage = new Text();

            addedMessage.setLayoutX(250);
            addedMessage.setLayoutY(370);
            addedMessage.setStyle("-fx-font: 24 arial");
            addedMessage.setFill(Color.WHITE);
            addedMessage.setId("added");

            if(!fieldTitle.getText().isEmpty() && !fieldAuthor.getText().isEmpty() && !fieldGenre.getText().isEmpty() && !fieldQuantity.getText().isEmpty()) {
                try {
                    addBook(fieldTitle.getText(), fieldAuthor.getText(), fieldGenre.getText(), Integer.parseInt(fieldQuantity.getText()));

                    addedMessage.setText("Book has been added");
                }catch (NumberFormatException ignored) {
                    addedMessage.setText("Wrong data");
                }
            }else {
                addedMessage.setText("Wrong data");
            }

            view.getChildren().add(addedMessage);
        };

        button.setOnAction(addBook);
    }

    private void eventGetDeleteBookView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getDeleteBookView());

        button.setOnAction(newView);
    }

    private void eventDeleteBook(Group view, Button button, TextField bookId, TextField quantity){
        EventHandler<ActionEvent> deleteBook = event -> {
            view.getChildren().remove(view.lookup("#delete"));
            Text deletedMessage = new Text();

            deletedMessage.setLayoutX(130);
            deletedMessage.setLayoutY(300);
            deletedMessage.setStyle("-fx-font: 24 arial");
            deletedMessage.setFill(Color.WHITE);
            deletedMessage.setId("delete");

            if(!bookId.getText().isEmpty()) {
                try {
                    if(quantity.getText().isEmpty()) {
                        deleteBook(Integer.parseInt(bookId.getText()), 0);
                    }else {
                        deleteBook(Integer.parseInt(bookId.getText()), Math.abs(Integer.parseInt(quantity.getText())));
                    }

                    deletedMessage.setText("Book has been deleted");
                }catch (NumberFormatException ignored) {
                    deletedMessage.setText("Wrong data");
                }
            }else {
                deletedMessage.setText("Wrong data");
            }

            view.getChildren().add(deletedMessage);
        };

        button.setOnAction(deleteBook);
    }

    private void eventGetMyBookListView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getMyBooksView());

        button.setOnAction(newView);
    }

    private void eventGetBookIndexView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getBookIndexView());

        button.setOnAction(newView);
    }

    private void eventGetRentBookView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getRentBookView());

        button.setOnAction(newView);
    }

    private void eventRentBook(Group view, Button button, TextField bookId, TextField title, TextField author) {
        EventHandler<ActionEvent> rentBook = event -> {
            view.getChildren().remove(view.lookup("#rented"));
            Text rentedMessage = new Text();

            rentedMessage.setLayoutX(80);
            rentedMessage.setLayoutY(350);
            rentedMessage.setStyle("-fx-font: 24 arial");
            rentedMessage.setFill(Color.WHITE);
            rentedMessage.setId("rented");

            try {
                if (!bookId.getText().isEmpty()) {
                    rentBook(Integer.parseInt(bookId.getText()), userName, null, null);
                    rentedMessage.setText("Book has been rented");
                } else if (!title.getText().isEmpty() && !author.getText().isEmpty()) {
                    rentBook(-1, this.userName, title.getText(), author.getText());
                    rentedMessage.setText("Book has been rented");
                }

            }catch (NumberFormatException ignored) {
                rentedMessage.setLayoutX(140);
                rentedMessage.setText("Wrong data");
            }catch (BookException.BookNotAvailable | BookException.BookAlreadyRented bookNotAvailable) {
                rentedMessage.setLayoutX(100);
                rentedMessage.setText(bookNotAvailable.getMessage());
            }

            view.getChildren().add(rentedMessage);
        };

        button.setOnAction(rentBook);
    }

    private void eventGetReturnBookView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getGetReturnBookView());

        button.setOnAction(newView);
    }

    private void eventReturnBook(Group view, Button button, TextField bookId, TextField title, TextField author) {
        EventHandler<ActionEvent> returnBook = event -> {
            view.getChildren().remove(view.lookup("#return"));
            Text returnBookMessage = new Text();

            returnBookMessage.setLayoutX(80);
            returnBookMessage.setLayoutY(350);
            returnBookMessage.setStyle("-fx-font: 24 arial");
            returnBookMessage.setFill(Color.WHITE);
            returnBookMessage.setId("return");

            try {
                if (!bookId.getText().isEmpty()) {
                    returnBook(Integer.parseInt(bookId.getText()), userName, null, null);
                    returnBookMessage.setText("Book has been returned");
                } else if (!title.getText().isEmpty() && !author.getText().isEmpty()) {
                    returnBook(-1, this.userName, title.getText(), author.getText());
                    returnBookMessage.setText("Book has been returned");
                }
            }catch (BookException.BookNotRented wrongBook) {
                returnBookMessage.setLayoutX(100);
                returnBookMessage.setText(wrongBook.getMessage());
            }catch (NumberFormatException ignore) {
                returnBookMessage.setLayoutX(140);
                returnBookMessage.setText("Wrong data");
            }

            view.getChildren().add(returnBookMessage);
        };

        button.setOnAction(returnBook);
    }

    private void eventGetMenuView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getAccountCockpitView());
        button.setOnAction(newView);
    }
}
