package classes.transactions;

import classes.Account;
import classes.Entity;
import classes.transaction.Transaction;
import enums.ETransactionTypes;

public class Transfer extends Transaction {
    private Account destinationAccount;

    public Transfer(double amount, Account inAccount, Account destinationAccount, Entity entity, ETransactionTypes transactionType) {
        //Llamando al otro constructor
        super(amount, inAccount, entity, transactionType);
        this.destinationAccount = destinationAccount;
        this.description = "Transferencia de: €" + this.amount + " hacia la cuenta: " + this.destinationAccount.getUUID();
    }

    public Transfer(double amount, String description, Account inAccount, Entity entity, ETransactionTypes transactionType) {
        //Llamando al otro constructor
        super(amount, inAccount, entity, transactionType);
        this.description = description;
    }

    @Override
    public boolean createTransaction() {
        try {
            if(this.inAccount.getAvalibleMoney()<this.amount) {
                System.out.println("Error al procesar la solicitud, el monto tiene que ser inferior o igual a su saldo actual");
                System.out.println("Su saldo actual es de: €" + this.inAccount.getAvalibleMoney());
                return false;
            } else {
                double newAvalibleMoneyInAccount = this.inAccount.getAvalibleMoney()-this.amount;
                double newAvalibleMoneyInDestinationAccount = this.destinationAccount.getAvalibleMoney()+this.amount;

                Transaction newTransaction = new Transfer(this.amount,
                        "Transferencia recibida de: €" + this.amount + " desde la cuenta: " + this.inAccount.getUUID(),
                        this.destinationAccount,this.destinationAccount.getEntity(),this.transactionType);


                this.destinationAccount.setAvalibleMoney(newAvalibleMoneyInDestinationAccount);
                this.inAccount.setAvalibleMoney(newAvalibleMoneyInAccount);

                this.amount*=-1;

                this.inAccount.addTransaction(this);
                this.inAccount.getEntity().addTransaction(this);

                this.destinationAccount.addTransaction(newTransaction);
                this.destinationAccount.getEntity().addTransaction(newTransaction);
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
