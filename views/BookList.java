package sample.views;

public class BookList extends java.awt.print.Book {
    private String id;
    private String title;
    private String genre;
    private String author;
    private String quantity;

    public BookList(String id, String title, String genre, String author, String quantity) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
