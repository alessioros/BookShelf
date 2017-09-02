package it.polimi.bookshelf.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.DateFormat;

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

    public ISBNDbFinder() {

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
                mBook.setPageCount(Integer.parseInt(pageCount));

            } catch (JSONException e) {
                mBook.setPageCount(0);
            }

            // ----- AUTHORS -----
            try {
                JSONArray authors = data.getJSONArray(KEY_AUTHORS);
                JSONObject author = authors.getJSONObject(0);

                mBook.setAuthor(author.getString("name"));

            } catch (Exception e) {
                mBook.setAuthor("");
            }

            // ----- PUBLISHER -----
            try {
                mBook.setPublisher(URLDecoder.decode(data.getString(KEY_PUBLISHER), UTF8));

            } catch (Exception e) {
                mBook.setPublisher("");
            }

            // ----- PUBLISHED DATE -----
            try {
                String date = data.getString(KEY_PUBLISHED_DATE).split(";")[1];
                mBook.setPublishedDate(DateFormat.getDateInstance().parse(date).toString());

            } catch (Exception e) {
                mBook.setPublishedDate("");
            }

            // ----- DESCRIPTION -----

        } catch (JSONException e) {

            return null;
        }
        return mBook;
    }

}
