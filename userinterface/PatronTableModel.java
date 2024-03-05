package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class PatronTableModel
{
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;

    //----------------------------------------------------------------------------
    public PatronTableModel(Vector<String> accountData)
    {
        name =  new SimpleStringProperty(accountData.elementAt(0));
        address =  new SimpleStringProperty(accountData.elementAt(1));
        city =  new SimpleStringProperty(accountData.elementAt(2));
        stateCode = new SimpleStringProperty(accountData.elementAt(3));
        zip = new SimpleStringProperty(accountData.elementAt(4));
        email = new SimpleStringProperty(accountData.elementAt(5));
        dateOfBirth = new SimpleStringProperty(accountData.elementAt(6));
    }

    //----------------------------------------------------------------------------
    public String getName() {
        return name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String aType) {
        name.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getAddress() {
        return address.get();
    }

    //----------------------------------------------------------------------------
    public void setAddress(String bal) {
        address.set(bal);
    }

    //----------------------------------------------------------------------------
    public String getCity() {
        return city.get();
    }

    //----------------------------------------------------------------------------
    public void setCity(String charge) {
        city.set(charge);
    }

    //----------------------------------------------------------------------------
    public String getStateCode() {
        return stateCode.get();
    }

    //----------------------------------------------------------------------------
    public void setStateCode(String charge) {
        stateCode.set(charge);
    }

    //----------------------------------------------------------------------------
    public String getZip() {
        return zip.get();
    }

    //----------------------------------------------------------------------------
    public void setZip(String charge) {
        zip.set(charge);
    }

    //----------------------------------------------------------------------------
    public String getEmail() {
        return email.get();
    }

    //----------------------------------------------------------------------------
    public void setEmail(String charge) {
        email.set(charge);
    }

    //----------------------------------------------------------------------------
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfBirth(String charge) {
        dateOfBirth.set(charge);
    }
}