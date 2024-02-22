package model;

// system imports
import model.model2.EntityBase;
import java.util.Properties;
import java.util.Vector;


public class PatronCollection extends EntityBase {
    private static final String myTableName = "Patron";
    private Vector<Patron> patronList; // Collection of Patron objects

    public PatronCollection(){
        super(myTableName);
        patronList = new Vector<Patron>();
    }

    public void updatePatronListFromSQL(String query) throws Exception {
        // Reset patronList
        this.patronList = new Vector<Patron>();

        // Pull the data
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // Loop through data received and make fill patronList with Patron objects
        for (int i = 0; i < allDataRetrieved.size(); i++) {
            this.patronList.add(new Patron(allDataRetrieved.elementAt(i)));
        }
    }




    public Vector<Patron> findPatronsOlderThan(String year) {

        //makes the entered string set to the first day for SQL syntax
        //if they only enter the year

        int len = year.length();
        if (len == 4){
            year = year + "-01-01";
        }

        // Pull the data
        String query = "SELECT * FROM " + myTableName + " WHERE dateOfBirth < '" + year + "'";


        try {
            updatePatronListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid Birth year. '" + year + "' is not valid!");
        }

        return this.patronList;
    }

    public Vector<Patron> findPatronsYoungerThan(String year) {

        //makes the entered string set to the first day for SQL syntax
        //if they only enter the year
        int len = year.length();
        if (len == 4){
            year = year + "-01-01";
        }

        // Pull the data
        String query = "SELECT * FROM " + myTableName + " WHERE dateOfBirth > " + '"' + year + '"';

        try {
            updatePatronListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid publication year. '" + year + "' is not valid!");
        }

        return this.patronList;
    }

    public Vector<Patron> findPatronsAtZipCode(String phrase) {
        String query = "SELECT * FROM " + myTableName + " WHERE zip LIKE '%" + phrase + "%'";

        try {
            updatePatronListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: '" + phrase + "' is an INVALID string.");
        }

        return this.patronList;
    }

    public Vector<Patron> findPatronsWithNameLike(String phrase) {
        String query = "SELECT * FROM " + myTableName + " WHERE name LIKE '%" + phrase + "%'";

        try {
            updatePatronListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: '" + phrase + "' is an INVALID string.");
        }

        return this.patronList;
    }

    //Overrides------
    @Override
    public Object getState(String key) {
        if (key.equals("Patrons"))
            return patronList;
        else
        if (key.equals("PatronList"))
            return this;
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }
}
