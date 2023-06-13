package classes.transaction;

import java.util.Date;

import classes.Account;
import classes.Entity;
import enums.ETransactionTypes;
import interfaces.ITransaction;

public abstract class Transaction implements ITransaction {

    protected String uuid;

    protected ETransactionTypes transactionType;

    protected double amount;

    protected Date transactionDate;

    protected String description;

    protected Account inAccount;

    public Transaction(double amount, Account inAccount, Entity entity, ETransactionTypes transactionType) {

        if(amount <0) {
            System.out.println("El monto no puede ser negativo");
            return;
        } else {
            this.amount = amount;
            this.inAccount = inAccount;
            this.transactionDate = new Date();
            this.transactionType = transactionType;

            //Obtener un universal Id unico para la transacción
            this.uuid = entity.createTransactionUUID();
        }
    }

    public Transaction(double amount, String description, Account inAccount, Entity entity, ETransactionTypes transactionType) {
        //Llamando al otro constructor
        this(amount, inAccount, entity, transactionType);

        //Cambiando el valor del memo
        this.description = description;
    }

    public Transaction(String uuid, double amount, Date transactionDate, String description, Account inAccount, ETransactionTypes transactionType) {
        this.uuid = uuid;
        this.amount = amount;
        this.inAccount = inAccount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.description = description;
    }

    public String getUUID() {
        return this.uuid;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getTransactionType() {
        return this.transactionType.toString();
    }

    public String getSummaryLine() {
        if(this.amount>= 0) {
            return String.format("%s : $%.02f : %s", this.transactionDate.toString(),
                    this.amount, this.description);
        }else {
            return String.format("%s : $(%.02f) : %s", this.transactionDate.toString(),
                    this.amount, this.description);
        }
    }

    public abstract boolean createTransaction();
}
