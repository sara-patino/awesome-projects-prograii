package classes.transactions;

import classes.Account;
import classes.Entity;
import enums.ETransactionTypes;
import classes.transactions.*;

public class Deposit extends Transaction {

	public Deposit(double amount, Account inAccount, Entity entity, ETransactionTypes transactionType) {
		// Llamando al otro constructor
		super(amount, inAccount, entity, transactionType);
		this.description = "Dep�sito de: �" + this.amount;

	}

	@Override
	public boolean createTransaction() {
		try {
			double newAvalibleMoney = this.inAccount.getAvalibleMoney() + this.amount;
			this.inAccount.setAvalibleMoney(newAvalibleMoney);
			this.inAccount.addTransaction(this);
			this.inAccount.getEntity().addTransaction(this);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
