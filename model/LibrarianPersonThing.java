// specify the package
package model;

// system imports
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;

import impresario.IView;

import model.EntityBase;

/** The class containing the AccountHolder  for the ATM application */
//==============================================================
public class LibrarianPersonThing extends EntityBase implements IView
{
    private static final String myTableName = "i don't like tables";

    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public LibrarianPersonThing(Properties props)
            throws InvalidPrimaryKeyException, PasswordMismatchException
    {
        super(myTableName);

//        String idToQuery = props.getProperty("ID");
//
//        String query = "SELECT * FROM " + myTableName + " WHERE (ID = " + idToQuery + ")";
//
//        Vector allDataRetrieved =  getSelectQueryResult(query);
//
//        // You must get one account at least
//        if (allDataRetrieved != null)
//        {
//            int size = allDataRetrieved.size();
//
//            // There should be EXACTLY one account. More than that is an error
//            if (size != 1)
//            {
//                throw new InvalidPrimaryKeyException("Multiple accounts matching user id : "
//                        + idToQuery + " found.");
//            }
//            else
//            {
//                // copy all the retrieved data into persistent state
//                Properties retrievedCustomerData = (Properties)allDataRetrieved.elementAt(0);
//                persistentState = new Properties();
//
//                Enumeration allKeys = retrievedCustomerData.propertyNames();
//                while (allKeys.hasMoreElements())
//                {
//                    String nextKey = (String)allKeys.nextElement();
//                    String nextValue = retrievedCustomerData.getProperty(nextKey);
//
//                    if (nextValue != null)
//                    {
//                        persistentState.setProperty(nextKey, nextValue);
//                    }
//                }
//
//            }
//        }
//        // If no account found for this user name, throw an exception
//        else
//        {
//            throw new InvalidPrimaryKeyException("No account matching user id : "
//                    + idToQuery + " found.");
//        }
//
//        String password = props.getProperty("Password");
//
//        String accountPassword = persistentState.getProperty("Password");
//
//        if (accountPassword != null)
//        {
//            boolean passwordCheck = accountPassword.equals(password);
//            if (!passwordCheck)
//            {
//                throw new PasswordMismatchException("Password mismatch");
//            }
//
//        }
//        else
//        {
//            throw new PasswordMismatchException("Password missing for account");
//        }

    }

    //----------------------------------------------------------
    public LibrarianPersonThing(String idToQuery)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        System.out.println("Haha sucker I'm ignoring the id stuff, here's the dummy id: " + idToQuery);

//        String query = "SELECT * FROM " + myTableName + " WHERE (ID = " + idToQuery + ")";
//
//        Vector allDataRetrieved =  getSelectQueryResult(query);
//
//        // You must get one account at least
//        if (allDataRetrieved != null)
//        {
//            int size = allDataRetrieved.size();
//
//            // There should be EXACTLY one account. More than that is an error
//            if (size != 1)
//            {
//                throw new InvalidPrimaryKeyException("Multiple accounts matching user id : "
//                        + idToQuery + " found.");
//            }
//            else
//            {
//                // copy all the retrieved data into persistent state
//                Properties retrievedCustomerData = (Properties)allDataRetrieved.elementAt(0);
//                persistentState = new Properties();
//
//                Enumeration allKeys = retrievedCustomerData.propertyNames();
//                while (allKeys.hasMoreElements())
//                {
//                    String nextKey = (String)allKeys.nextElement();
//                    String nextValue = retrievedCustomerData.getProperty(nextKey);
//
//                    if (nextValue != null)
//                    {
//                        persistentState.setProperty(nextKey, nextValue);
//                    }
//                }
//
//            }
//        }
//        // If no account found for this user name, throw an exception
//        else
//        {
//            throw new InvalidPrimaryKeyException("No account matching user id : "
//                    + idToQuery + " found.");
//        }
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        persistentState.setProperty(key, (String)value);

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}


