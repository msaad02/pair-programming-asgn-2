package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import model.EntityBase;

public class Patron extends EntityBase {
    // Class vars

    // Table name in DB
    private static final String myTableName = "Patron";
    private String updateStatusMessage = "";

    // Holds
    protected Properties dependencies;

    // constructor for this class
    //----------------------------------------------------------
    public Patron(String patronId) throws InvalidPrimaryKeyException {
        super(myTableName);

        // setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null && !allDataRetrieved.isEmpty()) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple accounts matching id : " + patronId + " found.");
            } else {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration<?> allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements()) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        // If no account found for this username, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No account matching id : " + patronId + " found.");
        }
    }

    public void update() // save()
    {
        updateStateInDatabase();
    }

    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("patronId") != null)
            {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("patronId",
                        persistentState.getProperty("patronId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Patron data for patron number : " + persistentState.getProperty("patronId") + " updated successfully in database!";
            }
            else
            {
                // insert
                Integer patronId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("patronId", "" + patronId.intValue());
                updateStatusMessage = "Account data for new account : " +  persistentState.getProperty("patronId")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing account data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    public Patron(Properties givenPatronData) {
        super(myTableName);

        persistentState = new Properties();

        Enumeration<?> allKeys = givenPatronData.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = givenPatronData.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    @Override
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage"))
            return "Updating status message?";

        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stateChangeRequest'");
    }

    /**
     * This method is needed solely to enable the Account information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("name"));
        v.addElement(persistentState.getProperty("address"));
        v.addElement(persistentState.getProperty("city"));
        v.addElement(persistentState.getProperty("stateCode"));
        v.addElement(persistentState.getProperty("zip"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("dateOfBirth"));

        return v;
    }

    @Override
    public String toString() {

        return "{" +
                "PatronID: " + this.getState("patronId") +
                ", Name: " + this.getState("name") +
                ", Address:" + this.getState("address") +
                ", City" + this.getState("city") +
                ", State Code: " + this.getState("stateCode") +
                ", Zip Code: " + this.getState("zip") +
                ", Email: " + this.getState("email") +
                ", Date of Birth: " + this.getState("dateOfBirth") +
                ", Status: " + this.getState("status") +
                "}";
    }
}