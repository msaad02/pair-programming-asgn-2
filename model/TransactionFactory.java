// specify the package
package model;

// system imports

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory {
    public static Transaction createTransaction(String transType) throws Exception {

        System.out.println("The transType in TransactionFactory is: " + transType);

        Transaction retValue = switch (transType) {
            case "insertBook" -> new InsertBookTransaction();
            case "insertPatron" -> new InsertPatronTransaction();
            case "searchBooks" -> new SearchBooksTransaction();
            case "searchPatrons" -> new SearchPatronsTransaction();
            default -> null;
        };

        return retValue;
    }
}
