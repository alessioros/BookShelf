package it.polimi.bookshelf.utilities;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import it.polimi.bookshelf.model.Author;
import it.polimi.bookshelf.model.Book;

@SuppressWarnings("FieldCanBeLocal")
public class AmazonFinder {

    private String AGILE_URL = "http://theagiledirector.com/getRest_v3.php?isbn=";
    private String KEY_LARGE_IMAGE = "LargeImage";
    private String KEY_URL = "URL";
    private String KEY_AUTHOR = "Author";
    private String KEY_TITLE = "Title";
    private String KEY_PUBLISHER = "Publisher";
    private String KEY_PUBL_DATE = "PublicationDate";
    private String KEY_FEATURE = "Feature";
    private String KEY_EAN = "EAN";
    private String text;

    public Book getBook(String ISBN) {

        Book amazonBook = new Book();
        boolean firstImage = false, alreadySetted = false;

        try {

            URL url = new URL(AGILE_URL + ISBN);

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser xmlParser = xmlFactoryObject.newPullParser();
            xmlParser.setInput(url.openConnection().getInputStream(), null);

            int event = xmlParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String name = xmlParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        //System.out.print("\n<"+name+">");
                        if (name.equals(KEY_LARGE_IMAGE) && !alreadySetted) {

                            firstImage = true;
                            alreadySetted = true;
                        }
                        break;

                    case XmlPullParser.TEXT:

                        text = xmlParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (name.equals(KEY_URL) && firstImage) {

                            amazonBook.setImgUrl(text);
                            firstImage = false;

                        } else if (name.equals(KEY_AUTHOR)) {

                            Author bookAuthor = new Author();

                            try{
                                bookAuthor.setName(text.split(" ")[0]);
                                bookAuthor.setSurname(text.split(" ")[1]);
                                amazonBook.setAuthorID(text);
                            }catch (NumberFormatException e){
                                Log.v("AMAZON EXCEPTION: ", "AUTHOR - "+e.toString());
                            }

                        } else if (name.equals(KEY_PUBLISHER)) {

                            amazonBook.setPublisher(text);

                        } else if (name.equals(KEY_PUBL_DATE)) {

                            Date date;
                            try{
                                date = DateFormat.getDateInstance().parse(text);
                            }catch (Exception e){
                                date = new Date();
                            }
                            amazonBook.setPublishedDate(date);

                        } else if (name.equals(KEY_FEATURE)) {

                            if (text.contains("Pagine:")) {

                                try {

                                    String pages = text.replace("Pagine:", "");
                                    pages = pages.replace(" ", "");
                                    amazonBook.setPageCount(pages);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (text.contains("Pages:")) {

                                try {

                                    String pages = text.replace("Pages:", "");
                                    pages = pages.replace(" ", "");
                                    amazonBook.setPageCount(pages);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } else if (name.equals(KEY_TITLE)) {

                            amazonBook.setTitle(text);

                        } else if (name.equals(KEY_EAN)) {

                            amazonBook.setISBN(text);
                        }

                        break;
                }
                event = xmlParser.next();
            }

        } catch (MalformedURLException murl) {
            return null;

        } catch (XmlPullParserException xec) {
            return null;

        } catch (IOException e) {
            return null;
        } catch(Exception e){
            Log.v("AMAZON EXCEPTION ", ""+e.toString());
            return null;
        }

        try{
            Log.v("AMAZON BOOK: ", "ISBN "+amazonBook.getISBN());
            Log.v("AMAZON BOOK: ", "AUTHOR ID "+amazonBook.getAuthorID());
            Log.v("AMAZON BOOK: ", "TITLE "+amazonBook.getTitle());
            Log.v("AMAZON BOOK: ", "DESCRIPTION "+amazonBook.getDescription());
            Log.v("AMAZON BOOK: ", "PUBDATE "+amazonBook.getPublishedDate().toString());
            Log.v("AMAZON BOOK: ", "PUBLISHER "+amazonBook.getPublisher());
            Log.v("AMAZON BOOK: ", "PAGE COUNT "+amazonBook.getPageCount());
            Log.v("AMAZON BOOK: ", "IMG URL "+amazonBook.getImgUrl());
        }catch (Exception e){
            Log.v("EXCEPTION",e.toString());
        }
        return amazonBook;

    }
}


