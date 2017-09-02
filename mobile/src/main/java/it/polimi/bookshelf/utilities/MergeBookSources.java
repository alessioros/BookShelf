package it.polimi.bookshelf.utilities;

import it.polimi.bookshelf.model.Book;

public class MergeBookSources {

    private boolean amazon, google, lt;

    public Book mergeBooks(Book amazonBook, Book googleBook, Book LTBook) {

        Book book = new Book();
        amazon = (amazonBook != null);
        google = (googleBook != null);
        lt = (LTBook != null);

        if (amazon && google && lt) {

            book.setISBN(mergeThree(amazonBook.getISBN(), googleBook.getISBN(), LTBook.getISBN()));
            book.setTitle(mergeThree(googleBook.getTitle(), amazonBook.getTitle(), LTBook.getTitle()));
            book.setAuthor(mergeThree(googleBook.getAuthor(), amazonBook.getAuthor(), LTBook.getAuthor()));

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (NullPointerException e) {
                return null;
            }

            book.setDescription(mergeThree(googleBook.getDescription(), LTBook.getDescription(), amazonBook.getDescription()));

            try{
                book.setPageCount(mergeThree(googleBook.getPageCount(), amazonBook.getPageCount(), LTBook.getPageCount()));
            }catch (NullPointerException e){
                book.setPageCount(0);
            }
            book.setPublishedDate(mergeThree(amazonBook.getPublishedDate(), googleBook.getPublishedDate(), LTBook.getPublishedDate()));
            book.setPublisher(mergeThree(amazonBook.getPublisher(), googleBook.getPublisher(), LTBook.getPublisher()));

            book.setImgUrl(mergeThree(amazonBook.getImgUrl(), googleBook.getImgUrl(), LTBook.getImgUrl()));

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }

        } else if (amazon && google && !lt) {

            book.setISBN(mergeTwo(amazonBook.getISBN(), googleBook.getISBN()));
            book.setTitle(mergeTwo(googleBook.getTitle(), amazonBook.getTitle()));
            book.setAuthor(mergeTwo(googleBook.getAuthor(), amazonBook.getAuthor()));

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (NullPointerException e) {
                return null;
            }

            book.setDescription(googleBook.getDescription());
            book.setPageCount(mergeTwo(googleBook.getPageCount(), amazonBook.getPageCount()));
            book.setPublishedDate(mergeTwo(amazonBook.getPublishedDate(), googleBook.getPublishedDate()));
            book.setPublisher(mergeTwo(amazonBook.getPublisher(), googleBook.getPublisher()));

            book.setImgUrl(mergeTwo(amazonBook.getImgUrl(), googleBook.getImgUrl()));

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }


        } else if (amazon && !google && lt) {

            book.setISBN(mergeTwo(amazonBook.getISBN(), LTBook.getISBN()));
            book.setTitle(mergeTwo(amazonBook.getTitle(), LTBook.getTitle()));
            book.setAuthor(mergeTwo(LTBook.getAuthor(), amazonBook.getAuthor()));

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (NullPointerException e) {
                return null;
            }

            book.setDescription(LTBook.getDescription());

            try {
                book.setPageCount(mergeTwo(LTBook.getPageCount(), amazonBook.getPageCount()));
            }catch(Exception e){
                book.setPageCount(0);
            }

            book.setPublishedDate(mergeTwo(amazonBook.getPublishedDate(), LTBook.getPublishedDate()));
            book.setPublisher(mergeTwo(amazonBook.getPublisher(), LTBook.getPublisher()));

            book.setImgUrl(mergeTwo(amazonBook.getImgUrl(), LTBook.getImgUrl()));

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }

        } else if (amazon && !google && !lt) {

            book.setISBN(amazonBook.getISBN());
            book.setTitle(amazonBook.getTitle());
            book.setAuthor(amazonBook.getAuthor());

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (NullPointerException e) {
                return null;
            }

            book.setDescription(amazonBook.getDescription());
            book.setPageCount(amazonBook.getPageCount());
            book.setPublishedDate(amazonBook.getPublishedDate());
            book.setPublisher(amazonBook.getPublisher());

            book.setImgUrl(amazonBook.getImgUrl());

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }

        } else if (!amazon && google && lt) {

            book.setISBN(mergeTwo(googleBook.getISBN(), LTBook.getISBN()));
            book.setTitle(mergeTwo(googleBook.getTitle(), LTBook.getTitle()));
            book.setAuthor(mergeTwo(googleBook.getAuthor(), LTBook.getAuthor()));

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (NullPointerException e) {
                return null;
            }

            book.setDescription(mergeTwo(googleBook.getDescription(), LTBook.getDescription()));
            book.setPageCount(mergeTwo(googleBook.getPageCount(), LTBook.getPageCount()));
            book.setPublishedDate(mergeTwo(googleBook.getPublishedDate(), LTBook.getPublishedDate()));
            book.setPublisher(mergeTwo(googleBook.getPublisher(), LTBook.getPublisher()));

            book.setImgUrl(mergeTwo(googleBook.getImgUrl(), LTBook.getImgUrl()));

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }

        } else if (!amazon && google && !lt) {

            book.setISBN(googleBook.getISBN());
            book.setTitle(googleBook.getTitle());
            book.setAuthor(googleBook.getAuthor());

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (Exception e) {

                return null;
            }

            book.setDescription(googleBook.getDescription());
            book.setPageCount(googleBook.getPageCount());
            book.setPublishedDate(googleBook.getPublishedDate());
            book.setPublisher(googleBook.getPublisher());

            book.setImgUrl(googleBook.getImgUrl());

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }

        } else if (!amazon && !google && lt) {

            book.setISBN(LTBook.getISBN());
            book.setTitle(LTBook.getTitle());
            book.setAuthor(LTBook.getAuthor());

            try {
                // mandatory info
                if (book.getISBN().equals("") || book.getTitle().equals(""))
                    return null;

            } catch (NullPointerException e) {
                return null;
            }

            book.setDescription(LTBook.getDescription());
            book.setPageCount(LTBook.getPageCount());
            book.setPublishedDate(LTBook.getPublishedDate());
            book.setPublisher(LTBook.getPublisher());

            book.setImgUrl(LTBook.getImgUrl());

            if (book.getImgUrl().equals("")) {

                book.setImgUrl("https://www.islandpress.org/sites/default/files/400px%20x%20600px-r01BookNotPictured.jpg");
            }

        } else {

            return null;
        }

        return book;
    }

    public String mergeTwo(String stringOne, String stringTwo) {

        if (stringOne != null && !stringOne.equals("")) {

            return stringOne;

        } else if (stringTwo != null) {

            return stringTwo;
        }

        return "";
    }

    public String mergeThree(String stringOne, String stringTwo, String stringThree) {

        if (stringOne != null && !stringOne.equals("")) {

            return stringOne;

        } else if (stringTwo != null && !stringTwo.equals("")) {

            return stringTwo;

        } else if (stringThree != null) {

            return stringThree;
        }

        return "";
    }

    public int mergeTwo(int one, int two) {

        if (one != 0) {

            return one;

        } else if (two != 0) {

            return two;
        }

        return 0;
    }

    public int mergeThree(int one, int two, int three) {

        if (one != 0) {

            return one;

        } else if (two != 0) {

            return two;

        } else if (three != 0) {

            return three;
        }

        return 0;
    }
}
