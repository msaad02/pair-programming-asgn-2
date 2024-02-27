package userinterface.userinterface2;

import impresario.IModel;
import userinterface.View;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("TellerView"))
		{
			return new TellerView(model);
		}
		else if(viewName.equals("TransactionChoiceView"))
		{
			return new TransactionChoiceView(model);
		}
		else if(viewName.equals("AccountCollectionView"))
		{
			return new AccountCollectionView(model);
		}
		else if(viewName.equals("AccountView"))
		{
			return new AccountView(model);
		}
		else if(viewName.equals("AccountHolderIDEntryView"))
		{
			return new AccountHolderIDEntryView(model);
		}
		else if(viewName.equals("DepositTransactionView"))
		{
			return new DepositTransactionView(model);
		}
		else if(viewName.equals("DepositAmountView"))
		{
			return new DepositAmountView(model);
		}
		else if(viewName.equals("WithdrawTransactionView"))
		{
			return new WithdrawTransactionView(model);
		}
		else if(viewName.equals("TransferTransactionView"))
		{
			return new TransferTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryTransactionView"))
		{
			return new BalanceInquiryTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryReceipt"))
		{
			return new BalanceInquiryReceipt(model);
		}
		else if(viewName.equals("WithdrawReceipt"))
		{
			return new WithdrawReceipt(model);
		}
		else if(viewName.equals("DepositReceipt"))
		{
			return new DepositReceipt(model);
		}
		else if(viewName.equals("TransferReceipt"))
		{
			return new TransferReceipt(model);
		}
		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
