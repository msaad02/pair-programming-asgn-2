// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the DepositTransaction for the ATM application */
//==============================================================
public class InsertPatronTransaction extends Transaction {

    private String depositAmount; // needed for GUI only

    // GUI Components
    private String transactionErrorMessage = "";
    private String accountUpdateStatusMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public InsertPatronTransaction() throws Exception {
        super();
        System.out.println("Constructor for create and insert Patron view");
        createAndShowInsertPatronTransactionView();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelInsertPatronTransaction", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("AccountNumber", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of inserting the Patron.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        System.out.println("enter `processTransaction` in InsertPatronTransaction.java");
        System.out.println("The props are: ");
        System.out.println(props);


        Patron newPatron = new Patron(props);
        newPatron.update();

        System.out.println("The new Patron has been added!");
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        } else if (key.equals("UpdateStatusMessage")) {
            return accountUpdateStatusMessage;
        } else if (key.equals("AccountNumberList")) {
            return myAccountIDs;
        } else if (key.equals("Account")) {
//            return myAccount;
        } else if (key.equals("DepositAmount")) {
            return depositAmount;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);
        if (key.equals("DoYourJob")) {
            System.out.println("DoYourJob enter if statement in `stateChangeRequest` InsertPatronTransaction.java");
//            doYourJob();
        }
        else if (key.equals("DoInsertPatronTransaction")) {
            System.out.println("DoInsertPatronTransaction enter if statement in `stateChangeRequest` in InsertPatronTransaction.java");

            processTransaction((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("DepositTransactionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("DepositTransactionView", this);
            currentScene = new Scene(newView);
            myViews.put("DepositTransactionView", currentScene);
        }
        return currentScene;
    }


    //------------------------------------------------------
    protected void createAndShowInsertPatronTransactionView() {
        View newView = ViewFactory.createView("InsertPatronTransactionView", this);
        Scene newScene = new Scene(newView);

        // make the view visible by installing it into the stage
        swapToView(newScene);
    }
}

