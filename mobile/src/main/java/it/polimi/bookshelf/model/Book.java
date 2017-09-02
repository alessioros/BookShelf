package it.polimi.bookshelf.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String ISBN;
    private String Title;
    private String Description;
    private Integer PageCount;
    private String Publisher;
    private String PublishedDate;
    private String imgUrl;
    private String Author;
    private String ShelfID;

    // default empty constructor
    public Book() {
    }

    // class constructor
    public Book(String ISBN, String Title, String Description, Integer PageCount, String Publisher, String PublishedDate, String Author, String ShelfID) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Description = Description;
        this.PageCount = PageCount;
        this.Publisher = Publisher;
        this.PublishedDate = PublishedDate;
        this.Author = Author;
        this.ShelfID = ShelfID;
    }

    protected Book(Parcel in) {
        ISBN = in.readString();
        Title = in.readString();
        Description = in.readString();
        PageCount = in.readInt();
        Publisher = in.readString();
        PublishedDate = in.readString();
        imgUrl = in.readString();
        Author = in.readString();
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
    public void setPageCount(Integer PageCount) {
        this.PageCount = PageCount;
    }

    public Integer getPageCount() {
        return this.PageCount;
    }
    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public String getPublisher() {
        return this.Publisher;
    }
    public void setPublishedDate(String PublishedDate) {
        this.PublishedDate = PublishedDate;
    }

    public String getPublishedDate() {
        return this.PublishedDate;
    }
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
}

    public String getImgUrl() {
    return this.imgUrl;
}

    public String getAuthor() {
        return this.Author;
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
        dest.writeInt(PageCount);
        dest.writeString(Publisher);
        dest.writeString(PublishedDate);
        dest.writeString(imgUrl);
        dest.writeString(Author);
        dest.writeString(ShelfID);
    }
}
