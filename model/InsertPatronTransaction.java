// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the WithdrawTransaction for the ATM application */
//==============================================================
public class InsertPatronTransaction extends Transaction
{
    private String withdrawAmount; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";
    private String accountUpdateStatusMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public InsertPatronTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("DoWithdraw", "TransactionError");
        dependencies.setProperty("CancelWithdraw", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        String accountNumber = props.getProperty("AccountNumber");
        String amount = props.getProperty("Amount");
        withdrawAmount = amount;

//        try
//        {
//            myAccount = createAccount(accountNumber);
//
//            boolean isOwner = myAccount.verifyOwnership(myCust);
//            if (!isOwner)
//            {
//                transactionErrorMessage = "ERROR: Withdraw Transaction: Not owner of selected account!!";
//                new Event(Event.getLeafLevelClassName(this), "processTransaction",
//                        "Failed to verify ownership of account number : " + accountNumber + ".",
//                        Event.ERROR);
//            }
//            else
//            {
//                boolean ok = myAccount.checkBalance(amount); // Not done via invocation of 'sCR(...)' method on Account as there is no possibility of callback
//                if (ok)
//                {
//                    myAccount.debit(amount);
//                    myAccount.update();
//                    accountUpdateStatusMessage = (String)myAccount.getState("UpdateStatusMessage");
//                    transactionErrorMessage = accountUpdateStatusMessage;
//
//                    createAndShowReceiptView();
//                }
//                else
//                {
//                    transactionErrorMessage = "Not enough money in account to withdraw $ " + amount;
//                }
//
//            }
//        }
//        catch (InvalidPrimaryKeyException ex)
//        {
//            transactionErrorMessage = "ACCOUNT FAILURE: Contact bank immediately!!";
//            new Event(Event.getLeafLevelClassName(this), "processTransaction",
//                    "Failed to create account for number : " + accountNumber + ". Reason: " + ex,
//                    Event.ERROR);
//
//        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError"))
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("UpdateStatusMessage"))
        {
            return accountUpdateStatusMessage;
        }
        else
        if (key.equals("AccountNumberList"))
        {
            return myAccountIDs;
        }
        else
        if (key.equals("Account"))
        {
            //return myAccount;
        }
        else
        if (key.equals("WithdrawAmount"))
        {
            return withdrawAmount;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        if (key.equals("DoYourJob"))
        {
            //doYourJob();
        }
        else
        if (key.equals("DoWithdraw"))
        {
            processTransaction((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the stage
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("WithdrawTransactionView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("WithdrawTransactionView", this);
            currentScene = new Scene(newView);
            myViews.put("WithdrawTransactionView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------
    protected void createAndShowReceiptView()
    {
        // create our initial view
        View newView = ViewFactory.createView("WithdrawReceipt", this);
        Scene newScene = new Scene(newView);

        myViews.put("WithdrawReceipt", newScene);

        // make the view visible by installing it into the stage
        swapToView(newScene);

    }

}

