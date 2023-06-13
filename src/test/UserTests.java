package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import classes.Account;
import classes.Entity;
import classes.User;

class UserTests {
    private static Entity entity;
    private static User user;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        entity = new Entity("Banco 1");
        user = new User("02015344K", "test@test.com", "Test", "Testting", "1234", entity);
    }

    @Test
    void getter() {
        String uuid, firstName, dni;
        int numAccounts;

        uuid = user.getUUID();
        firstName = user.getFirstName();
        dni = user.getDNI();
        numAccounts = user.getNumAccounts();

        assertNotEquals(uuid, "1234567");
        assertEquals(firstName, "Test");
        assertEquals(dni, "02015344K");
        assertEquals(numAccounts, 0);
    }

    @Test
    void validatePin() {
        assertEquals(user.validatePin("1234"), true);
        assertNotEquals(user.validatePin("1234"), false);
        assertNotEquals(user.validatePin("234"), true);
        assertNotEquals(user.validatePin("1234 "), true);
        assertNotEquals(user.validatePin(" 1234s"), true);
    }

    @Test
    void addAccount() {
        Account newAccount = new Account("Cuenta 1", user, entity);
        user.addAccount(newAccount);

        int numAccounts = user.getNumAccounts();
        Account accountUser = user.getAccount(0);

        assertEquals(numAccounts, 1);
        assertEquals(accountUser, newAccount);
    }

}