package it.polimi.bookshelf.model;

import java.util.List;

public class Author {

    private String ID;
    private String Name;
    private String Surname;
    private String Biography;
    private List<Integer> BookIDs;

    // default empty constructor
    public Author() {
    }

    // class constructor
    public Author(String ID, String Name, String Surname, String Biography) {
        this.ID = ID;
        this.Name = Name;
        this.Surname = Surname;
        this.Biography = Biography;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }
    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getSurname() {
        return this.Surname;
    }
    public void setBiography(String Biography) {
        this.Biography = Biography;
    }

    public String getBiography() {
        return this.Biography;
    }

    public void setBookIDs(List<Integer> BookIDs) {
        this.BookIDs = BookIDs;
    }

    public List<Integer> getBookIDs() {
        return this.BookIDs;
    }

    public void addBookID(int BookID) {
        this.BookIDs.add(BookID);
    }
}
