package classes;

import java.util.ArrayList;
import java.util.Objects;

import common.Files;
import interfaces.IUser;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements IUser{

    /**
     * ID único del usuario
     */
    private String uuid;

    /**
     * DNI del usuario
     */
    private String dni;

    /**
     * email del usuario
     */
    private String email;

    /**
     * Nombre del usuario
     */
    private String firstName;

    /**
     * Apellido del usuario
     */
    private String lastName;

    /**
     * Pin del usuario hasheado en MD5
     */
    private byte pinHash[];

    /**
     * Lista de cuentas del usuario
     */
    private ArrayList<Account> accounts;

    public User(String dni, String email, String firstName, String lastName, String pin, Entity theBank) {

        this.dni = dni;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;

        //Guardar el pin hasheado en MD5 para luego buscar el valor original
        //Esto se hace por seguridad.
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //Obtener un universal Id unico para el usuario
        this.uuid = theBank.createUserUUID();

        //Crear una lista vacía de cuentas
        this.accounts = new ArrayList<Account>();

    }

    public String getUUID() {
        return this.uuid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public int getNumAccounts() {
        return this.accounts.size();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAccount(Account onAcct) {
        this.accounts.add(onAcct);
    }

    public Account getAccount(int AccountIndex) {
        return this.accounts.get(AccountIndex);
    }

    public String getDNI() {
        return this.dni;
    }

    public boolean validatePin (String userPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(userPin.getBytes()), this.pinHash);
        } catch(NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return false;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\nResumen de cuentas de: %s\n", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n",a+1, this.accounts.get(a).getSummaryLine());
        }
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

}
