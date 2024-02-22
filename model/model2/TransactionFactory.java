// specify the package
package model.model2;

// system imports

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType,
		AccountHolder cust)
		throws Exception
	{
		Transaction retValue = null;

		if (transType.equals("Deposit"))
		{
			retValue = new DepositTransaction(cust);
		}
		else
		if (transType.equals("Withdraw"))
		{
			retValue = new WithdrawTransaction(cust);
		}
		else
		if (transType.equals("Transfer"))
		{
			retValue = new TransferTransaction(cust);
		}
		else
		if (transType.equals("BalanceInquiry"))
		{
			retValue = new BalanceInquiryTransaction(cust);
		}
		else
		if (transType.equals("ImposeServiceCharge"))
		{
			retValue = new ImposeServiceChargeTransaction(cust);
		}

		return retValue;
	}
}
