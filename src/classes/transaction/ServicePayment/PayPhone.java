package classes.transactions.servicePayment;

import classes.Account;
import classes.Entity;
import enums.ETransactionTypes;

public class PayPhone extends ServicePayment {
	public PayPhone(double amount, Account inAccount, Entity entity, ETransactionTypes transactionType) {
		super(amount, inAccount, entity, transactionType);
		this.description = "Pago de Servicio de Telefono";
	}
}
