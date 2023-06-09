package classes.transactions.servicePayment;

import classes.Account;
import classes.Entity;
import enums.ETransactionTypes;

public class PayWater extends ServicePayment {

	public PayWater(double amount,Account inAccount, Entity entity, ETransactionTypes transactionType){
		super(amount, inAccount, entity, transactionType);
		this.description = "Pago de servicio de agua";
	}
	
}
