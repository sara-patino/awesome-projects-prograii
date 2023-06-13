package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import classes.Account;
import classes.Entity;
import classes.User;

class AccountTests {

    private static Entity entity;
    private static User user;
    private static Account account;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        entity = new Entity("Banco 1");
        user = new User("02015344K", "test@test.com", "Test", "Testting", "1234", entity);
        account = new Account("Cuenta 1", user, entity);
        entity.addAccount(account);
        entity.addUser(user);
    }

    @Test
    void getter() {
        String uuid;
        Entity e;
        double balance, avalibleMoney;

        uuid = account.getUUID();
        balance = account.getBalance();
        avalibleMoney = account.getAvalibleMoney();
        e = account.getEntity();

        assertNotEquals(uuid, "1234567");
        assertEquals(balance, avalibleMoney);
        assertEquals(e, entity);
    }

    @Test
    void setAvalibleMoney() {
        double avalibleMoney;
        account.setAvalibleMoney(125.22);
        avalibleMoney = account.getAvalibleMoney();

        assertNotEquals(avalibleMoney, account.getBalance());
        assertNotEquals(avalibleMoney, 125);
        assertEquals(avalibleMoney, 125.22);
    }

}