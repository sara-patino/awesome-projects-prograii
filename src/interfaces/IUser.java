package interfaces;

import classes.Account;

public interface IUser {
    String getUUID();

    String getFirstName();

    int getNumAccounts();

    double getAcctBalance(int acctIdx);

    String getAcctUUID(int acctIdx);

    String getDNI();

    void addAccount(Account onAcct);

    Account getAccount(int AccountIndex);

    boolean validatePin(String userPin);

    void printAccountsSummary();

    void printAcctTransHistory(int acctIdx);
}