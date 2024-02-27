// specify the package
package model.model2;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.userinterface2.MainStageContainer;
import userinterface.View;
import userinterface.userinterface2.ViewFactory;
import userinterface.userinterface2.WindowPosition;

/** The class containing the Teller  for the ATM application */
//==============================================================
public class Teller implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private final ModelRegistry myRegistry;

	private AccountHolder myAccountHolder;

	// GUI Components
	private final Hashtable<String, Scene> myViews;
	private final Stage	  	myStage;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Teller()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Teller");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Teller",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		// createAndShowTellerView();
		createAndShowTransactionChoiceView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Login", "LoginError");
		dependencies.setProperty("Deposit", "TransactionError");
		dependencies.setProperty("Withdraw", "TransactionError");
		dependencies.setProperty("Transfer", "TransactionError");
		dependencies.setProperty("BalanceInquiry", "TransactionError");
		dependencies.setProperty("ImposeServiceCharge", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("LoginError"))
		{
			return loginErrorMessage;
		}
		else
		if (key.equals("TransactionError"))
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("Name"))
		{
			if (myAccountHolder != null)
			{
				return myAccountHolder.getState("Name");
			}
			else
				return "Undefined";
		}
		else
			return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("Teller.sCR: key = " + key);

		if (key.equals("Login"))
		{
			if (value != null)
			{
				loginErrorMessage = "";

				boolean flag = loginAccountHolder((Properties)value);
				if (flag)
				{
					createAndShowTransactionChoiceView();
				}
			}
		}
		else
		if (key.equals("CancelTransaction"))
		{
			createAndShowTransactionChoiceView();
		}
		else
		if ((key.equals("Deposit")) || (key.equals("Withdraw")) ||
			(key.equals("Transfer")) || (key.equals("BalanceInquiry")) ||
			(key.equals("ImposeServiceCharge")))
		{
			String transType = key;

			if (myAccountHolder != null)
			{
				doTransaction(transType);
			}
			else
			{
				transactionErrorMessage = "Transaction impossible: Customer not identified";
			}

		}
		else
		if (key.equals("Logout"))
		{
			myAccountHolder = null;
			myViews.remove("TransactionChoiceView");

			createAndShowTellerView();
		}

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}

	/**
	 * Login AccountHolder corresponding to user name and password.
	 */
	//----------------------------------------------------------
	public boolean loginAccountHolder(Properties props)
	{
		try
		{
			myAccountHolder = new AccountHolder(props);
			// DEBUG System.out.println("Account Holder: " + myAccountHolder.getState("Name") + " successfully logged in");
			return true;
		}
		catch (InvalidPrimaryKeyException ex)
		{
				loginErrorMessage = "ERROR: " + ex.getMessage();
				return false;
		}
		catch (PasswordMismatchException exec)
		{

				loginErrorMessage = "ERROR: " + exec.getMessage();
				return false;
		}
	}


	/**
	 * Create a Transaction depending on the Transaction type (deposit,
	 * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
	 * create.
	 */
	//----------------------------------------------------------
	public void doTransaction(String transactionType)
	{
		try
		{
			Transaction trans = TransactionFactory.createTransaction(
				transactionType, myAccountHolder);

			trans.subscribe("CancelTransaction", this);
			trans.stateChangeRequest("DoYourJob", "");
		}
		catch (Exception ex)
		{
			transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
			new Event(Event.getLeafLevelClassName(this), "createTransaction",
					"Transaction Creation Failure: Unrecognized transaction " + ex,
					Event.ERROR);
		}
	}

	//----------------------------------------------------------
	private void createAndShowTransactionChoiceView()
	{
		Scene currentScene = myViews.get("TransactionChoiceView");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("TransactionChoiceView", currentScene);
		}
				

		// make the view visible by installing it into the frame
		swapToView(currentScene);
		
	}

	//------------------------------------------------------------
	private void createAndShowTellerView()
	{
		Scene currentScene = myViews.get("TellerView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("TellerView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("TellerView", currentScene);
		}
				
		swapToView(currentScene);
		
	}


	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}



	//-----------------------------------------------------------------------------
	public void swapToView(Scene newScene)
	{

		
		if (newScene == null)
		{
			System.out.println("Teller.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);

	}

}

