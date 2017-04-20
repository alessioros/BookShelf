package it.polimi.bookshelf.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;

import it.polimi.bookshelf.model.Book;

@SuppressWarnings("FieldCanBeLocal")
public class GoogleBooksFinder {

    private String UTF8 = "UTF-8";

    private String KEY_ITEMS = "items";
    private String KEY_VOLUME = "volumeInfo";
    private String KEY_INDUSTRIAL_IDS = "industryIdentifiers";
    private String KEY_TYPE = "type";
    private String KEY_ISBN = "ISBN_13";
    private String KEY_IDENTIFIER = "identifier";
    private String KEY_IMAGES = "imageLinks";
    private String KEY_IMG_URL = "thumbnail";
    private String KEY_TITLE = "title";
    private String KEY_AUTHORS = "authors";
    private String KEY_PAGECOUNT = "pageCount";
    private String KEY_PUBLISHER = "publisher";
    private String KEY_PUBLISHED_DATE = "publishedDate";
    private String KEY_DESCRIPTION = "description";

    public GoogleBooksFinder() {
    }

    public Book findBook(JSONObject response, Book book) {

        Log.v("GOOGLE RESPONSE", response.toString());
        try {

            JSONArray jArray = response.getJSONArray(KEY_ITEMS);

            JSONObject volumeInfo = jArray.getJSONObject(0).getJSONObject(KEY_VOLUME);

            // ----- ISBN -----
            try {
                JSONArray industrialIDs = volumeInfo.getJSONArray(KEY_INDUSTRIAL_IDS);

                for (int i = 0; i < industrialIDs.length(); i++) {

                    if (industrialIDs.getJSONObject(i).getString(KEY_TYPE).equals(KEY_ISBN)) {
                        book.setISBN(industrialIDs.getJSONObject(i).getString(KEY_IDENTIFIER));
                    }
                }

                if (book.getISBN() == null || book.getISBN().equals("")) return null;
            } catch (Exception e) {
                return null;
            }

            // ----- BOOK COVER -----
            try {
                JSONObject imageLinks = volumeInfo.getJSONObject(KEY_IMAGES);

                book.setImgUrl(imageLinks.getString(KEY_IMG_URL));

            } catch (JSONException e) {
                book.setImgUrl("");
            }

            // ----- TITLE -----
            try {

                book.setTitle(URLDecoder.decode(volumeInfo.getString(KEY_TITLE), UTF8));

            } catch (Exception e) {
                book.setTitle("");
            }

            // ----- PAGE COUNT -----
            try {
                book.setPageCount(volumeInfo.getString(KEY_PAGECOUNT));

            } catch (JSONException e) {
                book.setPageCount("0");
            }

            // ----- AUTHORS -----
            try {
                JSONArray authors = volumeInfo.getJSONArray(KEY_AUTHORS);

                book.setAuthorID(URLDecoder.decode(authors.getString(0), UTF8));

            } catch (Exception e) {
                book.setAuthorID("0");
            }

            // ----- PUBLISHER -----
            try {
                book.setPublisher(URLDecoder.decode(volumeInfo.getString(KEY_PUBLISHER), UTF8));

            } catch (Exception e) {
                book.setPublisher("");
            }

            // ----- PUBLISHED DATE -----
            try {
                book.setPublishedDate(DateFormat.getDateInstance().parse(volumeInfo.getString(KEY_PUBLISHED_DATE)));

            } catch (Exception e) {
                book.setPublishedDate(new Date());
            }

            // ----- DESCRIPTION -----
            try {
                book.setDescription(URLDecoder.decode(volumeInfo.getString(KEY_DESCRIPTION), UTF8));

            } catch (Exception e) {
                book.setDescription("");
            }

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }

        try{
            Log.v("GOOGLE BOOK: ", "ISBN"+book.getISBN());
            Log.v("GOOGLE BOOK: ", "AUTHOR ID"+book.getAuthorID());
            Log.v("GOOGLE BOOK:", "TITLE"+book.getTitle());
            Log.v("GOOGLE BOOK:", "DESCRIPTION"+book.getDescription());
            Log.v("GOOGLE BOOK:", "PUBDATE"+book.getPublishedDate().toString());
            Log.v("GOOGLE BOOK:", "PUBLISHER"+book.getPublisher());
            Log.v("GOOGLE BOOK:", "PAGE COUNT"+book.getPageCount());
            Log.v("GOOGLE BOOK:", "IMG URL"+book.getImgUrl());
        }catch (Exception e){
            Log.v("EXCEPTION",e.toString());
        }
        return book;
    }

}
