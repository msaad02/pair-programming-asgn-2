// specify the package
package model;

// system imports
import model.model2.AccountHolder;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory {
    public static Transaction createTransaction(String transType, AccountHolder customer) throws Exception {

        Transaction retValue = switch (transType) {
            case "insertBook" -> new InsertBookTransaction(customer);
            case "insertPatron" -> new InsertPatronTransaction(customer);
            case "searchBooks" -> new SearchBooksTransaction(customer);
            case "searchPatrons" -> new SearchPatronsTransaction(customer);
            default -> null;
        };

        return retValue;
    }
}
