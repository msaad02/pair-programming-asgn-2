package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import model.model2.EntityBase;

public class Book extends EntityBase {

	// Table name in DB
	private static final String myTableName = "Book";

	// GUI components
	private String updateStatusMessage = "";

	// Holds
	protected Properties dependencies;

	// Constructor using AccountNumber ------------------------------------------------
	public Book(String accountNumber) throws InvalidPrimaryKeyException {
		super(myTableName);

		// setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (bookId = " + accountNumber + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one account at least
		if (allDataRetrieved != null && !allDataRetrieved.isEmpty()) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one account. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple accounts matching id : " + accountNumber + " found.");
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
			throw new InvalidPrimaryKeyException("No account matching id : " + accountNumber + " found.");
		}
	}

	// Constructor using Properties ------------------------------------------------
	public Book(Properties givenBookData) {
		super(myTableName);

		persistentState = new Properties();

		Enumeration<?> allKeys = givenBookData.propertyNames();
		while (allKeys.hasMoreElements()) {
			String nextKey = (String) allKeys.nextElement();
			String nextValue = givenBookData.getProperty(nextKey);

			if (nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

	public void update() // save()
	{
		updateStateInDatabase();
	}

	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase()
	{
		try
		{
			if (persistentState.getProperty("bookID") != null)
			{
				// update
				Properties whereClause = new Properties();
				whereClause.setProperty("bookID",
						persistentState.getProperty("bookID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Account data for account number : " + persistentState.getProperty("bookID") + " updated successfully in database!";
			}
			else
			{
				// insert
				Integer accountNumber = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("bookID", "" + accountNumber.intValue());
				updateStatusMessage = "Account data for new account : " +  persistentState.getProperty("bookID")
						+ "installed successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
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

	@Override
	public String toString() {
		// Get Title/Author/PubYear
		String title = (String) this.getState("bookTitle");
		String author = (String) this.getState("author");
		String pubYear = (String) this.getState("pubYear");

		return "Author: " + author + "; Title: " + title + "; Publication Year: " + pubYear;
	}
}