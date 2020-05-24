package sample.views;

public class UserBookList extends java.awt.print.Book {
    private String title;
    private String genre;
    private String author;
    private String rentDate;

    public UserBookList(String title, String genre, String author, String rentDate) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.rentDate = rentDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }
}
