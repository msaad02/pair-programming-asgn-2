// specify the package
package userinterface;

// system imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class InsertBookTransactionView extends View
{
    // GUI controls
    private TextField bookTitleTextbox;
    private TextField bookAuthorTextbox;
    private TextField bookPublicationYearTextbox;

    private ComboBox<String> bookStatusComboBox;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    // To prevent trying to insert invalid book information (like empty in everything)
    private boolean failToInsert = false;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertBookTransactionView(IModel trans)
    {
        super(trans, "InsertBookTransactionView");

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

        Text titleText = new Text("Insert a Book");
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
        // Book Title TextField
        Label title = new Label("Book Title:");
        grid.add(title, 0, 0);

        bookTitleTextbox = new TextField();
        bookTitleTextbox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(bookTitleTextbox, 1, 0);


        // ------------------------------------------------
        // Book Author TextField
        Label author = new Label("Book Author:");
        grid.add(author, 0, 1);

        bookAuthorTextbox = new TextField();
        bookAuthorTextbox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(bookAuthorTextbox, 1, 1);


        // ------------------------------------------------
        // Publication Year TextField
        Label pubYear = new Label("Publication Year:");
        grid.add(pubYear, 0, 2);

        bookPublicationYearTextbox = new TextField();
        bookPublicationYearTextbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(bookPublicationYearTextbox, 1, 2);


        // ------------------------------------------------
        // Book Status Combo Box
        Label status = new Label("Book Status:");
        grid.add(status, 0, 3);

        // Set combo box values and pick default
        ObservableList<String> data = FXCollections.observableArrayList("Active", "Inactive");
        bookStatusComboBox = new ComboBox<String>(data);
        bookStatusComboBox.getSelectionModel().select("Active");

        bookStatusComboBox.setMinSize(100, 20);
        grid.add(bookStatusComboBox, 1, 3);


        // ------------------------------------------------
        // Submit Button
        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                failToInsert = false;

                // Will update failToInsert if we shouldn't add books.
                processAction(e);

                if (!failToInsert) {
                    clearErrorMessage();

                    // do the inquiry
                    String bookTitle = bookTitleTextbox.getText();
                    String bookAuthor = bookAuthorTextbox.getText();
                    String bookPubYear = bookPublicationYearTextbox.getText();
                    String bookStatus = bookStatusComboBox.getValue();

                    processBookInsertion(bookTitle, bookAuthor, bookPubYear, bookStatus);

                    displayMessage("The book has been added!");

                    // Clear all the fields after they've been inserted.
                    // clearAllFields();
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
                myModel.stateChangeRequest("CancelInsertBookTransaction", null);
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

        String bookTitleEntered = bookTitleTextbox.getText();
        String bookAuthorEntered = bookAuthorTextbox.getText();
        String bookPublicationYearEntered = bookPublicationYearTextbox.getText();

        // Checking the book title entered
        if ((bookTitleEntered == null) || (bookTitleEntered.isEmpty())) {
            displayErrorMessage("Please enter a book title!");
            bookTitleTextbox.requestFocus();
            failToInsert = true;
            return;
        }

        // Checking the author title entered
        if ((bookAuthorEntered == null) || (bookAuthorEntered.isEmpty())) {
            displayErrorMessage("Please enter a book author!");
            bookAuthorTextbox.requestFocus();
            failToInsert = true;
            return;
        }

        // Checking the publication year entered
        if ((bookPublicationYearEntered == null) || (bookPublicationYearEntered.isEmpty())) {
            displayErrorMessage("Please enter a publication year!");
            bookPublicationYearTextbox.requestFocus();
            failToInsert = true;
            return;
        } else {
            int bookPublicationYearEnteredInt = Integer.parseInt(bookPublicationYearEntered);

            if ((bookPublicationYearEnteredInt < 1800) || (bookPublicationYearEnteredInt > 2024)) {
                displayErrorMessage("Invalid year! 1800 <= year <= 2024");
                bookPublicationYearTextbox.requestFocus();
                failToInsert = true;
                return;
            }
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
        bookTitleTextbox.setText("");
        bookAuthorTextbox.setText("");
        bookPublicationYearTextbox.setText("");
    }


    /**
     * Process account number selected by user.
     * Action is to pass this info on to the transaction object.
     */
    //----------------------------------------------------------
    private void processBookInsertion(String bookTitle, String bookAuthor, String bookPubYear, String bookStatus) {
        System.out.println("START BOOK INSERTION: inside `processBookInsertion` inside `InsertBookTransactionView`");

        Properties props = new Properties();

        props.setProperty("bookTitle", bookTitle);
        props.setProperty("author", bookAuthor);
        props.setProperty("pubYear", bookPubYear);
        props.setProperty("status", bookStatus); // MAY HAVE TO CHANGE IF STATUS IS NAMED DIFFERENT IN DB.

        myModel.stateChangeRequest("DoInsertBookTransaction", props);
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

