package userinterface;

// system imports
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

import model.BookCollection;
import model.Book;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

//==============================================================================
public class BookCollectionView extends View
{
    protected TableView<BookTableModel> tableOfAccounts;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public BookCollectionView(IModel wsc)
    {
        super(wsc, "BookCollectionView");

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

        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            BookCollection bookCollection = (BookCollection) myModel.getState("BookCollection");
            Vector<Book> entryList = (Vector<Book>) bookCollection.getState("Books");

            Enumeration<Book> entries = entryList.elements();

            while (entries.hasMoreElements())
            {
                Book nextAccount = (Book)entries.nextElement();

                Vector<String> view = nextAccount.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
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

        Text prompt = new Text("List of Books");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfAccounts = new TableView<BookTableModel>();
        tableOfAccounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn bookTitleColumn = new TableColumn("Title") ;
        bookTitleColumn.setMinWidth(100);
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<BookTableModel, String>("bookTitle"));

        TableColumn bookAuthorColumn = new TableColumn("Author") ;
        bookAuthorColumn.setMinWidth(100);
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<BookTableModel, String>("author"));

        TableColumn bookPubYearColumn = new TableColumn("Publication Year") ;
        bookPubYearColumn.setMinWidth(100);
        bookPubYearColumn.setCellValueFactory(new PropertyValueFactory<BookTableModel, String>("pubYear"));

        TableColumn bookStatusColumn = new TableColumn("Status") ;
        bookStatusColumn.setMinWidth(100);
        bookStatusColumn.setCellValueFactory(new PropertyValueFactory<BookTableModel, String>("status"));


        tableOfAccounts.getColumns().addAll(bookTitleColumn, bookAuthorColumn, bookPubYearColumn, bookStatusColumn);

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
                myModel.stateChangeRequest("CancelBookSearchTransaction", null);
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
        BookTableModel selectedItem = tableOfAccounts.getSelectionModel().getSelectedItem();

        // Something something selected item, not needed in this assignment...
//        if(selectedItem != null)
//        {
//            // String selectedAcctNumber = selectedItem.getBookID();
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