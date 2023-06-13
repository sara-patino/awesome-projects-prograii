package classes.transaction.ServicePayment;

import java.util.Date;

import classes.Account;
import classes.Entity;
import classes.transaction.Transaction;
import enums.ETransactionTypes;
import interfaces.IServicePayment;

public abstract class ServicePayment extends Transaction implements IServicePayment {
	protected double montToPay;
	protected Date maxDateToPay;
	
	ServicePayment(double amount,Account inAccount, Entity entity, ETransactionTypes transactionType) {
		super(amount, inAccount, entity, transactionType);
		Date actualDate = new Date();
		this.maxDateToPay = new Date(actualDate.getYear(),actualDate.getMonth(),actualDate.getDay()+ 5);
	}
	
	
	public void verifyInvoice() {
		this.montToPay = (Double)(Math.random()*100);
	}
	
	public void payService() {
		Date actualDate = new Date();
		this.maxDateToPay = new Date(actualDate.getYear(),actualDate.getMonth()+1,actualDate.getDay());
		System.out.println("El servicio fue pagado");
	};
	
	@Override
	public boolean createTransaction() {
		try {
			this.verifyInvoice();
			if(this.inAccount.getAvalibleMoney()<this.amount) {
				System.out.println("Error al procesar la solicitud, el monto a pagar tiene que ser inferior o igual a su saldo actual");
				System.out.println("Su saldo actual es de: �" + this.inAccount.getAvalibleMoney());
				return false;
			} else {
				double newAvalibleMoneyInAccount = this.inAccount.getAvalibleMoney()-this.amount;
				
				this.inAccount.setAvalibleMoney(newAvalibleMoneyInAccount);
				if(this.amount == this.montToPay) {
					this.payService();
				} else if (this.amount<this.montToPay) {
					double forPay = this.montToPay-this.amount;
					this.montToPay = forPay;
					System.out.println("\nTe falta por pagar: " + this.montToPay);
					System.out.println("Has pagado: " + this.amount);
				} else {
					double favorPay = this.amount-this.montToPay;
					System.out.println("\nHas pagado de m�s: " + favorPay);
					this.payService();
					this.montToPay -= favorPay;
					System.out.println("Has pagado: " + this.amount);
				}
				
				this.amount*=-1;
				
				this.inAccount.addTransaction(this);
				this.inAccount.getEntity().addTransaction(this);
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}
	}
	
}