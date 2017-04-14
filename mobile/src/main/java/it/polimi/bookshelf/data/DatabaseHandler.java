package it.polimi.bookshelf.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.polimi.bookshelf.model.Book;
import it.polimi.bookshelf.model.Author;
import it.polimi.bookshelf.model.Shelf;

public class DatabaseHandler {

    private DBHelper dbhelper;

    public DatabaseHandler(Context context) {
        dbhelper = new DBHelper(context);
    }

    public void insertBook(Book book) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        // choose your date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY);

        ContentValues cv = new ContentValues();

        cv.put(DatabaseStrings.BOOK_ID, book.getISBN());
        cv.put(DatabaseStrings.BOOK_TITLE, book.getTitle());
        cv.put(DatabaseStrings.BOOK_DESCRIPTION, book.getDescription());
        cv.put(DatabaseStrings.BOOK_PAGECOUNT, book.getPageCount());
        cv.put(DatabaseStrings.BOOK_PUBLISHER, book.getPublisher());
        cv.put(DatabaseStrings.BOOK_PUBLISHEDDATE, dateFormat.format(book.getPublishedDate()));
        cv.put(DatabaseStrings.BOOK_IMGURL, book.getImgUrl());
        cv.put(DatabaseStrings.AUTHOR_ID, book.getAuthorID());
        cv.put(DatabaseStrings.SHELF_ID, book.getShelfID());
        try {
            db.insert(DatabaseStrings.TBL_BOOK, null, cv);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }
    }

    public void insertAuthor(Author author) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DatabaseStrings.AUTHOR_ID, author.getID());
        cv.put(DatabaseStrings.AUTHOR_NAME, author.getName());
        cv.put(DatabaseStrings.AUTHOR_SURNAME, author.getSurname());
        cv.put(DatabaseStrings.AUTHOR_BIOGRAPHY, author.getBiography());

        try {
            db.insert(DatabaseStrings.TBL_AUTHOR, null, cv);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }
    }

    public void insertShelf(Shelf shelf) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DatabaseStrings.SHELF_ID, shelf.getName());
        cv.put(DatabaseStrings.SHELF_BOOKCOUNT, shelf.getBookCount());

        Log.v("SHELF INSERT: ", shelf.getName());
        Log.v("SHELF INSERT: ", ""+shelf.getBookCount());
        try {
            db.insert(DatabaseStrings.TBL_SHELF, null, cv);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
        }

        Shelf testS = this.queryShelf(shelf.getName());

        Log.v("SHELF INSERT: ", testS.getName());
        Log.v("SHELF INSERT: ", ""+testS.getBookCount());
    }

    public boolean deleteBook(String ISBN) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return db.delete(DatabaseStrings.TBL_BOOK, DatabaseStrings.BOOK_ID + "=?", new String[]{ISBN}) > 0;
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean deleteAuthor(String ID) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return db.delete(DatabaseStrings.TBL_AUTHOR, DatabaseStrings.AUTHOR_ID + "=?", new String[]{ID}) > 0;
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean deleteShelf(String name) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return db.delete(DatabaseStrings.TBL_SHELF, DatabaseStrings.SHELF_ID + "=?", new String[]{name}) > 0;
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public Book queryBook(String ISBN) {
        Cursor crs;
        Book Book = new Book();
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        String tableName = DatabaseStrings.TBL_BOOK;
        String primaryKey = DatabaseStrings.BOOK_ID;
        String[] whereArgs = new String[]{ISBN};

        try {
            String SQL_QUERY = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
            crs = db.rawQuery(SQL_QUERY, whereArgs);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return null;
        }

        // choose your date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY);
        Date date;

        crs.moveToFirst();
        while (!crs.isAfterLast()) {
            Book.setTitle(crs.getString(0));
            Book.setDescription(crs.getString(1));
            Book.setPageCount(crs.getString(2));
            Book.setPublisher(crs.getString(3));
            try {
                date = dateFormat.parse(crs.getString(4));
                Book.setPublishedDate(date);
            } catch (ParseException e) {
                Book.setPublishedDate(null);
                e.printStackTrace();
            }
            Book.setImgUrl(crs.getString(5));
            crs.moveToNext();
        }
        crs.close();

        return Book;
    }

    public Author queryAuthor(String ID) {
        Cursor crs;
        Author Author = new Author();
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        String tableName = DatabaseStrings.TBL_AUTHOR;
        String primaryKey = DatabaseStrings.AUTHOR_ID;
        String[] whereArgs = new String[]{ID};

        try {
            String SQL_QUERY = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
            crs = db.rawQuery(SQL_QUERY, whereArgs);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return null;
        }

        crs.moveToFirst();
        while (!crs.isAfterLast()) {
            Author.setName(crs.getString(0));
            Author.setSurname(crs.getString(1));
            Author.setBiography(crs.getString(2));
            crs.moveToNext();
        }
        crs.close();

        return Author;
    }

    public Shelf queryShelf(String name) {
        Cursor crs;
        Shelf shelf = new Shelf();
        shelf.setName(name);

        SQLiteDatabase db = dbhelper.getWritableDatabase();

        String tableName = DatabaseStrings.TBL_SHELF;
        String primaryKey = DatabaseStrings.SHELF_ID;
        String[] whereArgs = new String[]{name};

        try {
            String SQL_QUERY = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
            crs = db.rawQuery(SQL_QUERY, whereArgs);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return new Shelf();
        }

        crs.moveToFirst();
        while (!crs.isAfterLast()) {
            shelf.setBookCount(crs.getInt(0));
            crs.moveToNext();
        }
        crs.close();

        return shelf;
    }

    public List<Book> getBookList() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor crs;
        List<Book> BookList = new ArrayList<>();

        String tableName = DatabaseStrings.TBL_BOOK;

        try {
            String SQL_QUERY = "SELECT * FROM " + tableName;
            crs = db.rawQuery(SQL_QUERY, null);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return null;
        }

        // choose your date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY);
        Date date;

        crs.moveToFirst();
        while (!crs.isAfterLast()) {

            Book Book = new Book();

            Book.setTitle(crs.getString(0));
            Book.setDescription(crs.getString(1));
            Book.setPageCount(crs.getString(2));
            Book.setPublisher(crs.getString(3));
            try {
                date = dateFormat.parse(crs.getString(4));
                Book.setPublishedDate(date);
            } catch (ParseException e) {
                Book.setPublishedDate(null);
                e.printStackTrace();
            }
            Book.setImgUrl(crs.getString(5));

            BookList.add(Book);
            crs.moveToNext();
        }
        crs.close();

        return BookList;

    }

    public List<Author> getAuthorList() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor crs;
        List<Author> AuthorList = new ArrayList<>();

        String tableName = DatabaseStrings.TBL_AUTHOR;

        try {
            String SQL_QUERY = "SELECT * FROM " + tableName;
            crs = db.rawQuery(SQL_QUERY, null);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return null;
        }

        crs.moveToFirst();
        while (!crs.isAfterLast()) {

            Author Author = new Author();

            Author.setName(crs.getString(0));
            Author.setSurname(crs.getString(1));
            Author.setBiography(crs.getString(2));

            AuthorList.add(Author);
            crs.moveToNext();
        }
        crs.close();

        return AuthorList;

    }

    public List<Shelf> getShelfList() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor crs;
        List<Shelf> ShelfList = new ArrayList<>();

        String tableName = DatabaseStrings.TBL_SHELF;

        try {
            String SQL_QUERY = "SELECT * FROM " + tableName;
            crs = db.rawQuery(SQL_QUERY, null);
        } catch (SQLiteException sqle) {
            sqle.printStackTrace();
            return null;
        }

        crs.moveToFirst();
        while (!crs.isAfterLast()) {

            Shelf Shelf = new Shelf();

            Shelf.setBookCount(crs.getInt(0));

            ShelfList.add(Shelf);
            crs.moveToNext();
        }
        crs.close();

        return ShelfList;

    }
}
