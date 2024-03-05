package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import model.PatronCollection;
import model.Patron;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

//==============================================================================
public class PatronCollectionView extends View
{
    protected TableView<PatronTableModel> tableOfAccounts;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public PatronCollectionView(IModel wsc)
    {
        super(wsc, "PatronCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));
        container.setPrefWidth(500); // Set the preferred width to 600 pixels, adjust as necessary


        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {

        ObservableList<PatronTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            PatronCollection patronCollection = (PatronCollection) myModel.getState("PatronCollection");
            Vector<Patron> entryList = (Vector<Patron>) patronCollection.getState("Patrons");

            Enumeration<Patron> entries = entryList.elements();

            while (entries.hasMoreElements())
            {
                Patron nextAccount = (Patron)entries.nextElement();

                Vector<String> view = nextAccount.getEntryListView();

                // add this list entry to the list
                PatronTableModel nextTableRowData = new PatronTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfAccounts.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
            System.out.println("\n\nWe crashed... :(");
            System.out.println("The error was: " + e);
        }
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text("Brockport Library");
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

        Text prompt = new Text("List of Patrons");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfAccounts = new TableView<PatronTableModel>();
        tableOfAccounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn patronTitleColumn = new TableColumn("Name") ;
        patronTitleColumn.setMinWidth(100);
        patronTitleColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("name"));

        TableColumn patronAuthorColumn = new TableColumn("Address") ;
        patronAuthorColumn.setMinWidth(100);
        patronAuthorColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("address"));

        TableColumn patronPubYearColumn = new TableColumn("City") ;
        patronPubYearColumn.setMinWidth(100);
        patronPubYearColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("city"));

        TableColumn patronStateCodeColumn = new TableColumn("State Code") ;
        patronStateCodeColumn.setMinWidth(100);
        patronStateCodeColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("stateCode"));

        TableColumn patronZipCodeColumn = new TableColumn("Zip Code") ;
        patronZipCodeColumn.setMinWidth(100);
        patronZipCodeColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("zip"));

        TableColumn patronEmailColumn = new TableColumn("Email") ;
        patronEmailColumn.setMinWidth(100);
        patronEmailColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("email"));

        TableColumn patronDateOfBirthColumn = new TableColumn("Date of Birth") ;
        patronDateOfBirthColumn.setMinWidth(100);
        patronDateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, String>("dateOfBirth"));


        tableOfAccounts.getColumns().addAll(
                patronTitleColumn,
                patronAuthorColumn,
                patronPubYearColumn,
                patronStateCodeColumn,
                patronZipCodeColumn,
                patronEmailColumn,
                patronDateOfBirthColumn
        );

        tableOfAccounts.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processAccountSelected();
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfAccounts);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                // do the inquiry
                processAccountSelected();

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
                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
                 * to the model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelPatronSearchTransaction", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }



    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    protected void processAccountSelected()
    {
        PatronTableModel selectedItem = tableOfAccounts.getSelectionModel().getSelectedItem();

        // Something something selected item, not needed in this assignment...
//        if(selectedItem != null)
//        {
//            // String selectedAcctNumber = selectedItem.getPatronID();
//
//            // Is a part of useless class for now, doesn't matter.
//            // myModel.stateChangeRequest("AccountSelected", selectedAcctNumber);
//        }
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processAccountSelected();
		}
	}
   */

}