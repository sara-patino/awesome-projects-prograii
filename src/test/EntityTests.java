package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import classes.Account;
import classes.Entity;
import classes.User;
import classes.transaction.Transaction;
import classes.transaction.Deposit;
import enums.ETransactionTypes;

class EntityTests {

    private static Entity entity;
    private static User user;
    private static Account account;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        entity = new Entity("Banco 1");
        user = new User("02015344K", "test@test.com", "Test", "Testting", "1234", entity);
        account = new Account("Cuenta 1", user, entity);
    }

    @Test
    void createUUID() {
        String userUUID = entity.createUserUUID(),
                accountUUID = entity.createAccountUUID(),
                transactionUUID = entity.createTransactionUUID();

        assertNotEquals(userUUID, accountUUID);
        assertNotEquals(userUUID, transactionUUID);
        assertNotEquals(transactionUUID, accountUUID);

        assertNotEquals(transactionUUID, "123456");
        assertNotEquals(accountUUID, "1234567");
        assertNotEquals(userUUID, "1484548");
    }

    @Test
    void addAccount() {
        entity.addAccount(account);
        Account newAccount = new Account("Cuenta 2", user, entity);
        entity.addAccount(newAccount);

        assertEquals(entity.getAccounts().get(0), account);
        assertEquals(entity.getAccounts().get(1), newAccount);
    }

    @Test
    void addUser() {
        entity.addUser(user);
        User newUser = new User("55847699K", "test1@test.com", "Test1", "Testting1", "2345", entity);
        entity.addUser(newUser);

        assertEquals(entity.getUsers().get(0), user);
        assertEquals(entity.getUsers().get(1), newUser);
    }

    @Test
    void addTransaction() {
        Transaction newTransaction = new Deposit(125, account, entity, ETransactionTypes.deposit);
        entity.addTransaction(newTransaction);

        assertEquals(entity.getTransactions().get(0), newTransaction);
    }

    @Test
    void userLogin() {
        User authuser = entity.userLogin("02015344K", "1234");
        User authuser1 = entity.userLogin("0201534K", "1234");
        User authuser2 = entity.userLogin("02015344K", "1234 ");

        assertEquals(authuser, user);
        assertNotEquals(authuser1, user);
        assertNotEquals(authuser2, user);
    }

}