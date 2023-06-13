package classes.transactions;

import classes.Account;
import classes.Entity;
import classes.transaction.Transaction;
import enums.ETransactionTypes;

public class Withdrawal extends Transaction {
    public Withdrawal(double amount, Account inAccount, Entity entity, ETransactionTypes transactionType) {
        //Llamando al otro constructor
        super(amount, inAccount, entity, transactionType);
        this.description = "Retiro de: €" + this.amount;

    }

    @Override
    public boolean createTransaction() {
        try {
            if(this.inAccount.getAvalibleMoney()<this.amount) {
                System.out.println("Error al procesar la solicitud, el monto tiene que ser inferior o igual a su saldo actual");
                System.out.println("Su saldo actual es de: €" + this.inAccount.getAvalibleMoney());
                return false;
            } else {
                double newAvalibleMoney = this.inAccount.getAvalibleMoney()-this.amount;
                this.inAccount.setAvalibleMoney(newAvalibleMoney);
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
