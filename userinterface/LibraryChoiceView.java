// specify the package
package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class LibraryChoiceView extends View
{

    // other private data
//    private final int labelWidth = 120;
//    private final int labelHeight = 25;

    // GUI components
    private Button insertBookButton;
    private Button insertPatronButton;
    private Button searchBooksButton;
    private Button searchPatronsButton;
    private Button doneButton;

    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibraryChoiceView(IModel teller)
    {
        super(teller, "TransactionChoiceView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    private VBox createTitle()
    {
        VBox container = new VBox(12);
        Text titleText = new Text("LIBRARY SYSTEM");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }


    // Create the navigation buttons
    //-------------------------------------------------------------
    private VBox createFormContents()
    {

        VBox container = new VBox(15);

        // create the buttons, listen for events, add them to the container

        // Insert Book Button
        HBox dCont = new HBox(10);
        dCont.setAlignment(Pos.CENTER);
        insertBookButton = new Button("Insert Book");
        insertBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        insertBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("insertBook", null);
            }
        });
        dCont.getChildren().add(insertBookButton);

        container.getChildren().add(dCont);

        // Insert Patron Button
        HBox wCont = new HBox(10);
        wCont.setAlignment(Pos.CENTER);
        insertPatronButton = new Button("Insert Patron");
        insertPatronButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        insertPatronButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("insertPatron", null);
            }
        });
        wCont.getChildren().add(insertPatronButton);

        container.getChildren().add(wCont);

        // Search Books Button
        HBox tCont = new HBox(10);
        tCont.setAlignment(Pos.CENTER);
        searchBooksButton = new Button("Search Books");
        searchBooksButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        searchBooksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("searchBooks", null);
            }
        });
        tCont.getChildren().add(searchBooksButton);

        container.getChildren().add(tCont);

        // Search Patrons Button
        HBox biCont = new HBox(10);
        biCont.setAlignment(Pos.CENTER);
        searchPatronsButton = new Button("Search Patrons");
        searchPatronsButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        searchPatronsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("searchPatrons", null);
            }
        });
        biCont.getChildren().add(searchPatronsButton);

        container.getChildren().add(biCont);

        // Add white space to follow design spec
        container.getChildren().add(new Label(" "));

        // Done Button
        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("Done");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });
        doneCont.getChildren().add(doneButton);

        container.getChildren().add(doneCont);

        return container;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {

    }


    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TransactionError"))
        {
            // display the passed text
            displayErrorMessage((String)value);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}


