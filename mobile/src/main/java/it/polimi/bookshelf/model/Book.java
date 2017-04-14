package it.polimi.bookshelf.model;

import java.util.Date;

public class Book {

    private String ISBN;
    private String Title;
    private String Description;
    private String PageCount;
    private String Publisher;
    private Date PublishedDate;
    private String ImgUrl;
    private int AuthorID;
    private int ShelfID;

    // default empty constructor
    public Book() {}

    // class constructor
    public Book(String ISBN, String Title, String Description, String PageCount, String Publisher, Date PublishedDate, String ImgUrl, int AuthorID, int ShelfID) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Description = Description;
        this.PageCount = PageCount;
        this.Publisher = Publisher;
        this.PublishedDate = PublishedDate;
        this.ImgUrl = ImgUrl;
        this.AuthorID = AuthorID;
        this.ShelfID = ShelfID;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISBN() {
        return this.ISBN;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTitle() {
        return this.Title;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return this.Description;
    }
    public void setPageCount(String PageCount) {
        this.PageCount = PageCount;
    }

    public String getPageCount() {
        return this.PageCount;
    }
    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public String getPublisher() {
        return this.Publisher;
    }
    public void setPublishedDate(Date PublishedDate) {
        this.PublishedDate = PublishedDate;
    }

    public Date getPublishedDate() {
        return this.PublishedDate;
    }
    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }

    public String getImgUrl() {
        return this.ImgUrl;
    }

    public void setAuthorID(int AuthorID) {
        this.AuthorID = AuthorID;
    }

    public int getAuthorID() {
        return this.AuthorID;
    }
    public void setShelfID(int ShelfID) {
        this.ShelfID = ShelfID;
    }

    public int getShelfID() {
        return this.ShelfID;
    }
}
