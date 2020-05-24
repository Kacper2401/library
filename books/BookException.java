package sample.books;

public class BookException {
    public static class BookNotAvailable extends Exception{
        public BookNotAvailable() {
            super("Book not available");
        }
    }

    public static class BookAlreadyRented extends  Exception{
        public BookAlreadyRented() {
            super("Book already rented");
        }
    }

    public static class BookNotRented extends Exception{
        public BookNotRented() {super("Book not rented");}
    }
}
