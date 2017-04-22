package it.polimi.bookshelf.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Book implements Parcelable{

    private String ISBN;
    private String Title;
    private String Description;
    private String PageCount;
    private String Publisher;
    private Date PublishedDate;
    private String ImgUrl;
    private String AuthorID;
    private String ShelfID;

    // default empty constructor
    public Book() {}

    // class constructor
    public Book(String ISBN, String Title, String Description, String PageCount, String Publisher, Date PublishedDate, String ImgUrl, String AuthorID, String ShelfID) {
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

    protected Book(Parcel in) {
        ISBN = in.readString();
        Title = in.readString();
        Description = in.readString();
        PageCount = in.readString();
        Publisher = in.readString();
        ImgUrl = in.readString();
        AuthorID = in.readString();
        ShelfID = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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

    public void setAuthorID(String AuthorID) {
        this.AuthorID = AuthorID;
    }

    public String getAuthorID() {
        return this.AuthorID;
    }
    public void setShelfID(String ShelfID) {
        this.ShelfID = ShelfID;
    }

    public String getShelfID() {
        return this.ShelfID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ISBN);
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeString(PageCount);
        dest.writeString(Publisher);
        dest.writeString(ImgUrl);
        dest.writeString(AuthorID);
        dest.writeString(ShelfID);
    }
}
