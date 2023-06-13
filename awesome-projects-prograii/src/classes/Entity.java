package classes;

import java.util.ArrayList;
import java.util.Random;

import classes.transaction.Transaction;

public class Entity {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    private ArrayList<Transaction> transactions;

    public Entity (String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.users = new ArrayList<User>();
        this.transactions = new ArrayList<Transaction>();
    }

    public String createUserUUID() {
        String uuid;

        Random rng = new Random();
        int len=6;
        boolean nonUnique = false;

        do {
            // generar id unico
            uuid = "";
            for (int c=0; c<len;c++) {
                uuid+= ((Integer)rng.nextInt(10)).toString();
            }

            // chequear que es unico
            for(User u: this.users) {
                if(uuid.compareTo(u.getUUID())==0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return uuid;
    }

    public String createAccountUUID() {
        String uuid;

        Random rng = new Random();
        int len=10;
        boolean nonUnique = false;

        do {
            // generar id unico
            uuid = "";
            for (int c=0; c<len;c++) {
                uuid+= ((Integer)rng.nextInt(10)).toString();
            }

            // chequear que es unico
            for(Account a: this.accounts) {
                if(uuid.compareTo(a.getUUID())==0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return uuid;
    }

    public String createTransactionUUID() {
        String uuid;

        Random rng = new Random();
        int len=15;
        boolean nonUnique = false;

        do {
            // generar id unico
            uuid = "";
            for (int c=0; c<len;c++) {
                uuid+= ((Integer)rng.nextInt(10)).toString();
            }

            // chequear que es unico
            for(Transaction t: this.transactions) {
                if(uuid.compareTo(t.getUUID())== 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return uuid;
    }

    public void addAccount(Account onAcct) {
        this.accounts.add(onAcct);
    }

    public void addUser(User newUser) {
        this.users.add(newUser);
    }

    public void addTransaction(Transaction newTransaction) {
        this.transactions.add(newTransaction);
    }

    public User userLogin(String userID, String pin) {

        //buscar en la lista de usuarios
        for(User u: this.users) {
            if((u.getUUID().compareTo(userID) == 0||u.getDNI().compareTo(userID)==0) && u.validatePin(pin)) {
                return u;
            }
        }

        //Si no se encuentra el usuario o el pin es incorrecto
        return null;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }
}
