// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import userinterface.PatronCollectionView;
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the DepositTransaction for the ATM application */
//==============================================================
public class SearchPatronsTransaction extends Transaction {

    // Class vars
    private PatronCollection patronList;
    private Patron selectedPatron;

    // GUI Components
    private String transactionErrorMessage = "";
    private String accountUpdateStatusMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public SearchPatronsTransaction() throws Exception {
        super();
        System.out.println("Constructor for create and insert patron view");
        createAndShowSearchPatronTransactionView();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchPatronTransaction", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("AccountNumber", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of inserting the patron.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        String zipQuery = props.getProperty("zip");

        this.patronList = new PatronCollection();
        Vector<Patron> patronList = this.patronList.findPatronsAtZipCode(zipQuery);
        System.out.println("The matched patrons are : " + patronList);
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        } else if (key.equals("UpdateStatusMessage")) {
            return accountUpdateStatusMessage;
        } else if (key.equals("PatronCollection")) {
            return patronList.getState("PatronCollection");
        } else if (key.equals("Patrons")) {
            return patronList.getState("Patrons");
        }
//        } else if (key.equals("AccountNumberList")) {
//            return myAccountIDs;
//        } else if (key.equals("Account")) {
////            return myAccount;
//        } else if (key.equals("DepositAmount")) {
//            return depositAmount;
//        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("DepositTransaction.sCR: key: " + key);
        if (key.equals("DoYourJob")) {
            System.out.println("DoYourJob enter if statement in `stateChangeRequest` SearchPatronTransaction.java");
//            doYourJob();
        }
        else if (key.equals("DoSearchPatronTransaction")) {
            System.out.println("DoSearchPatronTransaction enter if statement in `stateChangeRequest` in SearchPatronTransaction.java");

            processTransaction((Properties) value);
            createAndShowSearchPatronCollectionView();
        }
        else if (key.equals("CancelPatronSearchTransaction")) {
            createAndShowSearchPatronTransactionView();
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
    protected void createAndShowSearchPatronTransactionView() {
        View newView = ViewFactory.createView("SearchPatronTransactionView", this);
        Scene newScene = new Scene(newView);

        // make the view visible by installing it into the stage
        swapToView(newScene);
    }


    //------------------------------------------------------
    protected void createAndShowSearchPatronCollectionView() {
        View newView = ViewFactory.createView("PatronCollectionView", this);
        Scene newScene = new Scene(newView);

        // make the view visible by installing it into the stage
        swapToView(newScene);
    }

}

