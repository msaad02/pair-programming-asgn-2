package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

    public static View createView(String viewName, IModel model)
    {
        if(viewName.equals("LibraryChoiceView"))
        {
            return new LibraryChoiceView(model);
        }
        else
            return null;
    }
}
