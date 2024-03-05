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
public class InsertPatronTransactionView extends View
{
    // GUI controls
    private TextField patronNameTextbox;
    private TextField patronAddressTextbox;
    private TextField patronCityTextbox;
    private TextField patronStateCodeTextbox;
    private TextField patronZipTextbox;
    private TextField patronEmailTextbox;
    private TextField patronDateOfBirthTextbox;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    // To prevent trying to insert invalid patron information (like empty in everything)
    private boolean failToInsert = false;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertPatronTransactionView(IModel trans)
    {
        super(trans, "InsertPatronTransactionView");

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

        Text titleText = new Text("Insert a Patron");
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
        // Patron Name TextField
        Label name = new Label("Patron Name:");
        grid.add(name, 0, 0);

        patronNameTextbox = new TextField();
        patronNameTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronNameTextbox, 1, 0);


        // ------------------------------------------------
        // Patron Address TextField
        Label address = new Label("Patron Address:");
        grid.add(address, 0, 1);

        patronAddressTextbox = new TextField();
        patronAddressTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronAddressTextbox, 1, 1);


        // ------------------------------------------------
        // Patron City TextField
        Label city = new Label("Patron City:");
        grid.add(city, 0, 2);

        patronCityTextbox = new TextField();
        patronCityTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronCityTextbox, 1, 2);


        // ------------------------------------------------
        // Publication Year TextField
        Label stateCode = new Label("Patron State Code:");
        grid.add(stateCode, 0, 3);

        patronStateCodeTextbox = new TextField();
        patronStateCodeTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronStateCodeTextbox, 1, 3);


        // ------------------------------------------------
        // Patron Zipcode TextField
        Label zipCode = new Label("Patron Zipcode:");
        grid.add(zipCode, 0, 4);

        patronZipTextbox = new TextField();
        patronZipTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronZipTextbox, 1, 4);


        // ------------------------------------------------
        // Patron Email TextField
        Label email = new Label("Patron Email:");
        grid.add(email, 0, 5);

        patronEmailTextbox = new TextField();
        patronEmailTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronEmailTextbox, 1, 5);


        // ------------------------------------------------
        // Patron Date of Birth TextField
        Label dateOfBirth = new Label("Patron Date of Birth:");
        grid.add(dateOfBirth, 0, 6);

        patronDateOfBirthTextbox = new TextField();
        patronDateOfBirthTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(patronDateOfBirthTextbox, 1, 6);


        // ------------------------------------------------
        // Submit Button
        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                failToInsert = false;

                // Will update failToInsert if we shouldn't add patrons.
                processAction(e);

                if (!failToInsert) {
                    clearErrorMessage();

                    // do the inquiry
                    String patronName = patronNameTextbox.getText();
                    String patronAddress = patronAddressTextbox.getText();
                    String patronCity = patronCityTextbox.getText();
                    String patronState = patronStateCodeTextbox.getText();
                    String patronZip = patronZipTextbox.getText();
                    String patronEmail = patronEmailTextbox.getText();
                    String patronDateOfBirth = patronDateOfBirthTextbox.getText();

                    processPatronInsertion(
                        patronName,
                        patronAddress,
                        patronCity,
                        patronState,
                        patronZip,
                        patronEmail,
                        patronDateOfBirth
                    );

                    displayMessage("The patrons have been added!");

                    // Clear all the fields after they've been inserted.
                    clearAllFields();
                }
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
                myModel.stateChangeRequest("CancelInsertPatronTransaction", null);
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
    public void processAction(Event evt)
    {
        // DEBUG: System.out.println("TellerView.actionPerformed()");

        clearErrorMessage();

        String patronNameEntered = patronNameTextbox.getText();
        String patronAddressEntered = patronAddressTextbox.getText();
        String patronCityEntered = patronCityTextbox.getText();
        String patronStateEntered = patronStateCodeTextbox.getText();
        String patronZipEntered = patronZipTextbox.getText();
        String patronEmailEntered = patronEmailTextbox.getText();
        String patronDateOfBirthEntered = patronDateOfBirthTextbox.getText();

        if ((patronNameEntered == null) || (patronNameEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron name!");
            patronNameTextbox.requestFocus();
            failToInsert = true;
        } else if ((patronAddressEntered == null) || (patronAddressEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron address!");
            patronAddressTextbox.requestFocus();
            failToInsert = true;
        } else if ((patronCityEntered == null) || (patronCityEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron city!");
            patronCityTextbox.requestFocus();
            failToInsert = true;
        } else if ((patronStateEntered == null) || (patronStateEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron state!");
            patronStateCodeTextbox.requestFocus();
            failToInsert = true;
        } else if ((patronZipEntered == null) || (patronZipEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron zip code!");
            patronZipTextbox.requestFocus();
            failToInsert = true;
        } else if ((patronEmailEntered == null) || (patronEmailEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron email!");
            patronEmailTextbox.requestFocus();
            failToInsert = true;
        } else if ((patronDateOfBirthEntered == null) || (patronDateOfBirthEntered.isEmpty())) {
            displayErrorMessage("Please enter a valid patron date of birth!");
            patronDateOfBirthTextbox.requestFocus();
            failToInsert = true;
        }
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void clearAllFields()
    {
        patronNameTextbox.setText("");
        patronAddressTextbox.setText("");
        patronCityTextbox.setText("");
        patronStateCodeTextbox.setText("");
        patronZipTextbox.setText("");
        patronEmailTextbox.setText("");
        patronDateOfBirthTextbox.setText("");
    }


    /**
     * Process account number selected by user.
     * Action is to pass this info on to the transaction object.
     */
    //----------------------------------------------------------
    private void processPatronInsertion(
            String patronName,
            String patronAddress,
            String patronCity,
            String patronState,
            String patronZip,
            String patronEmail,
            String patronDateOfBirth
    ) {
        System.out.println("START BOOK INSERTION: inside `processPatronInsertion` inside `InsertPatronTransactionView`");

        Properties props = new Properties();

        props.setProperty("name", patronName);
        props.setProperty("address", patronAddress);
        props.setProperty("city", patronCity);
        props.setProperty("stateCode", patronState);
        props.setProperty("zip", patronZip);
        props.setProperty("email", patronEmail);
        props.setProperty("dateOfBirth", patronDateOfBirth);

        myModel.stateChangeRequest("DoInsertPatronTransaction", props);
    }

    /**
     * Required by interface, but has no role here
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value) {  }

    /**
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

