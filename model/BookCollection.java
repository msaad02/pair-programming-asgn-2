package model;

import model.model2.EntityBase;

// system imports
import java.util.Properties;
import java.util.Vector;

public class BookCollection extends EntityBase {

    private static final String myTableName = "Book";
    private Vector<Book> bookList;

    // ------ START MATT CHANGES ------

    public BookCollection() {
        super(myTableName);
        bookList = new Vector<Book>();
    }


    public void updateBookListFromSQL(String query) throws Exception {
        // Reset bookList
        this.bookList = new Vector<Book>();

        // Pull the data
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // Loop through data received and make fill bookList with Book objects
        for (int i = 0; i < allDataRetrieved.size(); i++) {
            this.bookList.add(new Book(allDataRetrieved.elementAt(i)));
        }
    }


    public Vector<Book> findBooksOlderThanDate(String year) {

        String query = "SELECT * FROM " + myTableName + " WHERE pubYear < " + year;

        try {
            updateBookListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid publication year. '" + year + "' is not valid!");
        }

        return this.bookList;
    }

    public Vector<Book> findBooksNewerThanDate(String year) {

        // Pull the data
        String query = "SELECT * FROM " + myTableName + " WHERE pubYear > " + year;

        try {
            updateBookListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid publication year. '" + year + "' is not valid!");
        }

        return this.bookList;
    }

    public Vector<Book> findBooksWithTitleLike(String phrase) {
        String query = "SELECT * FROM " + myTableName + " WHERE bookTitle LIKE '%" + phrase + "%'";

        try {
            updateBookListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: '" + phrase + "' is an INVALID string.");
        }

        return this.bookList;
    }

    public Vector<Book> findBooksWithAuthorLike(String phrase) {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + phrase + "%'";

        try {
            updateBookListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: '" + phrase + "' is an INVALID string.");
        }

        return this.bookList;
    }

//    // TEMPLATE FOR FUNCTIONS
//    public Vector<Book> findWhatever(String parameter) {
//
//        String query = "<SQL STATEMENT>";
//
//        try {
//            updateBookListFromSQL(query);
//        } catch (Exception e) {
//            throw new Error("<ERROR MESSAGE>");
//        }
//
//        return this.bookList;
//    }

    // ------ END MATT CHANGES ------

    @Override
    public Object getState(String key) {
        if (key.equals("Books"))
            return bookList;
        else
        if (key.equals("BookList"))
            return this;
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }
}