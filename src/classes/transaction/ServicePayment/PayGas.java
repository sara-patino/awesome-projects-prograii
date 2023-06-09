package classes.transactions.servicePayment;

import classes.Account;
import classes.Entity;
import enums.ETransactionTypes;

public class PayGas extends ServicePayment {
	public PayGas(double amount,Account inAccount, Entity entity, ETransactionTypes transactionType){
		super(amount, inAccount, entity, transactionType);
		this.description = "Pago de servicio de Gas";
	}
}
