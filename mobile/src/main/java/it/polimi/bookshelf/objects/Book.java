package it.polimi.bookshelf.objects;

public class Book {

    public String book_isbn;
    public String book_title;
    public String book_author;
    public String user_id;
    public String shelf_id;

    public Book() {

    }

    public Book(String book_isbn, String book_title, String book_author, String user_id, String shelf_id) {
        this.book_isbn = book_isbn;
        this.book_title = book_title;
        this.book_author = book_author;
        this.user_id = user_id;
        this.shelf_id = shelf_id;
    }
}
