package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.Account;
import classes.Entity;
import classes.User;
import classes.transactions.Deposit;
import classes.transactions.Transaction;
import classes.transactions.Transfer;
import classes.transactions.Withdrawal;
import enums.ETransactionTypes;

class TransactionTests {

    private static Entity entity;
    private static User user;
    private static Account account;
    private static Account account2;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        entity = new Entity("Banco 1");
        user = new User("02015344K", "test@test.com", "Test", "Testting", "1234", entity);
        account = new Account("Cuenta 1", user, entity);
        account2 = new Account("Cuenta 2", user, entity);
    }

    @Test
    void deposit() {
        Transaction newTransaction = new Deposit(125, account, entity, ETransactionTypes.deposit);
        newTransaction.createTransaction();
        double amount = newTransaction.getAmount();

        assertEquals(amount, 125);
    }

    @Test
    void withdrawal() {
        Transaction newTransaction = new Withdrawal(50, account, entity, ETransactionTypes.deposit);
        newTransaction.createTransaction();
        double amount = newTransaction.getAmount();

        assertEquals(amount, 50);
    }

    @Test
	void transfer() {
		Transaction newTransaction = new Transfer(50,account,account2,entity,ETransactionTypes.deposit);
		double amount = newTransaction.getAmount();
		
		
		assertEquals(amount, 50);
	}

}