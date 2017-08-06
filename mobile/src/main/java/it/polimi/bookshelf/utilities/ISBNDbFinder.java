package it.polimi.bookshelf.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;

import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.data.DatabaseHandler;
import it.polimi.bookshelf.model.Author;
import it.polimi.bookshelf.model.Book;

public class ISBNDbFinder {

    private String UTF8 = "UTF-8";

    private String KEY_ITEMS = "data";
    private String KEY_ISBN = "isbn13";
    private String KEY_TITLE = "title_latin";
    private String KEY_AUTHORS = "author_data";
    private String KEY_PAGECOUNT = "physical_description";
    private String KEY_PUBLISHER = "publisher_text";
    private String KEY_PUBLISHED_DATE = "edition_info";
    private DataHandler dH;

    public ISBNDbFinder(Context context) {
        this.dH = new DataHandler(context);
    }

    public Book getBook(JSONObject response) {

        Book mBook = new Book();

        try {

            JSONArray jArray = response.getJSONArray(KEY_ITEMS);

            JSONObject data = jArray.getJSONObject(0);

            // ----- ISBN -----
            try {
                mBook.setISBN(data.getString(KEY_ISBN));
                if (mBook.getISBN() == null || mBook.getISBN().equals("")) return null;
            } catch (Exception e) {
                return null;
            }

            // ----- BOOK COVER -----

            // ----- TITLE -----
            try {

                mBook.setTitle(URLDecoder.decode(data.getString(KEY_TITLE), UTF8));

            } catch (Exception e) {
                mBook.setTitle("");
            }

            // ----- PAGE COUNT -----
            try {
                String physicalDesc = data.getString(KEY_PAGECOUNT);
                String pageCount = physicalDesc.split(";")[physicalDesc.split(";").length - 1];
                pageCount = pageCount.replace(" ", "").replace("pages", "");
                mBook.setPageCount(pageCount);

            } catch (JSONException e) {
                mBook.setPageCount("0");
            }

            // ----- AUTHORS -----
            try {
                JSONArray authors = data.getJSONArray(KEY_AUTHORS);
                JSONObject author = authors.getJSONObject(0);

                Author bookAuthor = new Author();
                bookAuthor.setName(author.getString("name").split(",")[1].replace(" ", ""));
                bookAuthor.setSurname(author.getString("name").split(",")[0].replace(" ", ""));
                bookAuthor.setID(author.getString("id"));
                Log.v("AUTHOR NAME ", bookAuthor.getName());
                Log.v("AUTHOR SURNAME ", bookAuthor.getSurname());
                Log.v("AUTHOR ID ", bookAuthor.getID());

                try {
                    DatabaseHandler dbH = dH.getDatabaseHandler();
                    dbH.insertAuthor(bookAuthor);
                } catch (SQLiteConstraintException e) {
                    Log.v("ISBNDB FINDER", "AUTHOR ALREADY INSERTED " + e.toString());
                } catch (Exception e) {
                    Log.v("ISBNDB FINDER", "AUTHOR ALREADY INSERTED " + e.toString());
                }

                mBook.setAuthorID(bookAuthor.getID());

            } catch (Exception e) {
                mBook.setAuthorID("");
            }

            // ----- PUBLISHER -----
            try {
                mBook.setPublisher(URLDecoder.decode(data.getString(KEY_PUBLISHER), UTF8));
                Log.v("BOOK PUBLISHER", mBook.getPublisher());

            } catch (Exception e) {
                mBook.setPublisher("");
            }

            // ----- PUBLISHED DATE -----
            try {
                String date = data.getString(KEY_PUBLISHED_DATE).split(";")[1];
                mBook.setPublishedDate(DateFormat.getDateInstance().parse(date));
                Log.v("BOOK PUBLISHED DATE", mBook.getPublishedDate().toString());

            } catch (Exception e) {
                mBook.setPublishedDate(new Date());
            }

            // ----- DESCRIPTION -----

        } catch (JSONException e) {

            return null;
        }

        try {
            Log.v("ISBN BOOK: ", "ISBN " + mBook.getISBN());
            Log.v("ISBN BOOK: ", "AUTHOR ID " + mBook.getAuthorID());
            Log.v("ISBN BOOK:", "TITLE " + mBook.getTitle());
            Log.v("ISBN BOOK:", "DESCRIPTION " + mBook.getDescription());
            Log.v("ISBN BOOK:", "PUBDATE " + mBook.getPublishedDate().toString());
            Log.v("ISBN BOOK:", "PUBLISHER " + mBook.getPublisher());
            Log.v("ISBN BOOK:", "PAGE COUNT " + mBook.getPageCount());
            Log.v("ISBN BOOK:", "IMG URL " + mBook.getImgUrl());
        } catch (Exception e) {
            Log.v("EXCEPTION", e.toString());
        }

        return mBook;
    }

}
