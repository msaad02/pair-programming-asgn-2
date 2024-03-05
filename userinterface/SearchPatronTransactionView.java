// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Balance Inquiry Transaction View  for the ATM application */
//==============================================================
public class SearchPatronTransactionView extends View
{
    // GUI controls
    private TextField patronZipQueryTextbox;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;


    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchPatronTransactionView(IModel trans)
    {
        super(trans, "SearchPatronTransactionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                "));

        getChildren().add(container);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text("Search a Patron");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // ------------------------------------------------
        // Patron Title TextField
        Label zip = new Label("Patron Zip:");
        grid.add(zip, 0, 0);

        patronZipQueryTextbox = new TextField();
        patronZipQueryTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronZipQueryTextbox, 1, 0);


        // ------------------------------------------------
        // Submit Button
        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processAction(e);
            }
        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the teller to
                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its model (controller) that the balance inquiry transaction was canceled, and leaves it
                 * to the model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelSearchPatronTransaction", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------
    public void processAction(Event evt) {
        // DEBUG: System.out.println("TellerView.actionPerformed()");

        // Perform any validation here, but the assignment doesn't ask for it right now.
        String patronTitleQueryEntered = patronZipQueryTextbox.getText();

        processPatronSearch(patronTitleQueryEntered);
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Process account number selected by user.
     * Action is to pass this info on to the transaction object.
     */
    //----------------------------------------------------------
    private void processPatronSearch(String patronZipQuery) {
        // Pass it off to the transaction object
        Properties props = new Properties();

        props.setProperty("zip", patronZipQuery);

        myModel.stateChangeRequest("DoSearchPatronTransaction", props);
    }

    /**
     * Required by interface, but has no role here
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value) {  }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display message
     */
    //----------------------------------------------------------
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }


    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}

//---------------------------------------------------------------
//	Revision History:

