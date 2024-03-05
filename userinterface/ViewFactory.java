package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {
    public static View createView(String viewName, IModel model) {
        // Switched to a switch statement for readability
        return switch (viewName) {
            case "LibraryChoiceView" -> new LibraryChoiceView(model);
            case "InsertBookTransactionView" -> new InsertBookTransactionView(model);
            case "InsertPatronTransactionView" -> new InsertPatronTransactionView(model);
            case "SearchBookTransactionView" -> new SearchBookTransactionView(model);
            case "SearchPatronTransactionView" -> new SearchPatronTransactionView(model);
            case "BookCollectionView" -> new BookCollectionView(model);
            case "PatronCollectionView" -> new PatronCollectionView(model);
            default -> null;
        };
    }
}
