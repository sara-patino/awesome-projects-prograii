package classes.transaction.ServicePayment;

import classes.Account;
import classes.Entity;
import enums.ETransactionTypes;

public class PayLight extends ServicePayment {
	public PayLight(double amount,Account inAccount, Entity entity, ETransactionTypes transactionType){
		super(amount, inAccount, entity, transactionType);
		this.description = "Pago de servicio de Luz";
	}
}
