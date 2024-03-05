package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class BookTableModel
{
    private final SimpleStringProperty bookTitle;
    private final SimpleStringProperty author;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> accountData)
    {
        bookTitle =  new SimpleStringProperty(accountData.elementAt(0));
        author =  new SimpleStringProperty(accountData.elementAt(1));
        pubYear =  new SimpleStringProperty(accountData.elementAt(2));
        status = new SimpleStringProperty(accountData.elementAt(3));
    }

    //----------------------------------------------------------------------------
    public String getBookTitle() {
        return bookTitle.get();
    }

    //----------------------------------------------------------------------------
    public void setBookTitle(String aType) {
        bookTitle.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getAuthor() {
        return author.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor(String bal) {
        author.set(bal);
    }

    //----------------------------------------------------------------------------
    public String getPubYear() {
        return pubYear.get();
    }

    //----------------------------------------------------------------------------
    public void setPubYear(String charge) {
        pubYear.set(charge);
    }

    //----------------------------------------------------------------------------
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String charge) {
        status.set(charge);
    }
}