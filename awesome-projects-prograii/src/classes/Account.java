package classes;

import java.util.ArrayList;

import classes.transaction.Transaction;

public class Account {

    private String uuid;

    private String name;

    private double avalibleMoney;

    private User holder;

    private Entity entity;

    private ArrayList<Transaction> transactions;

    public Account (String name, User holder, Entity theBank) {

        this.name = name;
        this.holder = holder;
        this.entity = theBank;
        this.avalibleMoney = 0;

        //Obtener id unico de la cuenta
        this.uuid = theBank.createAccountUUID();

        //Array vac�o de transacciones
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;
    }

    public double getBalance() {
        double balance = 0;
        for(Transaction t:this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public double getAvalibleMoney() {
        return this.avalibleMoney;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setAvalibleMoney(double newAvalibleMoney) {
        this.avalibleMoney = newAvalibleMoney;
    }

    public String getSummaryLine() {

        //Obtener el balance de cuentas
        double balance = this.getBalance();

        if(balance>=0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public void printTransHistory() {

        System.out.printf("\n Historico de transacciones de la cuenta %s\n", this.uuid);
        for(int t = this.transactions.size()-1;t>=0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
    }

    public void addTransaction (Transaction newTrans) {
        this.transactions.add(newTrans);
    }
}
