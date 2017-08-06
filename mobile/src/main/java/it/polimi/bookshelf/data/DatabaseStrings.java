package it.polimi.bookshelf.data;

class DatabaseStrings {

    /* Strings for table Book*/
    static final String TBL_BOOK = "Book";
    static final String BOOK_ID = "ISBN";
    static final String BOOK_TITLE = "title";
    static final String BOOK_DESCRIPTION = "description";
    static final String BOOK_PAGECOUNT = "pageCount";
    static final String BOOK_PUBLISHER = "publisher";
    static final String BOOK_PUBLISHEDDATE = "publishedDate";
    static final String BOOK_IMGURL = "imgUrl";

    /* Strings for table Author*/
    static final String TBL_AUTHOR = "Author";
    static final String AUTHOR_ID = "ID";
    static final String AUTHOR_NAME = "name";
    static final String AUTHOR_SURNAME = "surname";
    static final String AUTHOR_BIOGRAPHY = "biography";

    /* Strings for table Shelf*/
    static final String TBL_SHELF = "Shelf";
    static final String SHELF_ID = "name";
    static final String SHELF_BOOKCOUNT = "bookCount";

}
