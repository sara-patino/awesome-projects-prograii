package client;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

import classes.Entity;
import classes.User;
import classes.transaction.Deposit;
import classes.transaction.Transaction;
import classes.transactions.Transfer;
import classes.transactions.Withdrawal;
import classes.transaction.ServicePayment.PayGas;
import classes.transaction.ServicePayment.PayLight;
import classes.transaction.ServicePayment.PayPhone;
import classes.transaction.ServicePayment.PayWater;
import enums.ETransactionTypes;
import classes.ATMManagement;
import classes.Account;

public class Menu {

    public static Entity ATMMainView(ATMManagement atmMg, Scanner sc) {
        ArrayList<Entity> entities = new ArrayList<Entity>(atmMg.getEntities());
        int choice = 0;
        final int maxChoice = entities.size();
        do {
            try {

                int index = 1;

                System.out.println("Seleccione un Banco:");
                for (Entity e : entities) {
                    System.out.printf("%d) %s\n", index, e.getName());
                    index++;
                }
                choice = sc.nextInt();
                if (choice < 1 || (choice > maxChoice && choice != 123456789)) {
                    System.out.println("Esa opción no está permitida eliga una opción entre [1-" + maxChoice + "]");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (choice < 1 || (choice > maxChoice && choice != 123456789));
        if (choice != 123456789) {
            return entities.get(choice - 1);
        } else {
            Menu.adminView(atmMg, sc);
            return null;
        }
    }

    public static void adminView(ATMManagement atmMg, Scanner sc) {
        int choice = 0;
        do {
            try {

                System.out.println("Seleccione una opción:");
                System.out.println("1) Crear un nuevo Banco");
                System.out.println("2) Elegir un Banco");
                System.out.println("3) Salir");
                choice = sc.nextInt();
                if (choice < 1 || choice > 3) {
                    System.out.println("Esa opción no está permitida eliga una opción entre [1-3]");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                Menu.createEntity(atmMg, sc);
                break;
            default:
                break;
        }
    }

    public static void createEntity(ATMManagement atmMg, Scanner sc) {
        String name;
        try {

            sc.nextLine();
            System.out.print("Ingrese el nombre del banco: ");
            name = sc.nextLine();
            Entity newEntity = new Entity(name);
            atmMg.addEntity(newEntity);

        } catch (InputMismatchException e) {
            System.err.println(" ");
            System.err.println("|==Has introducido un valor no permitido            ==|");
            sc.nextLine();
        }
    }

    public static User bankMainView(Entity entity, Scanner sc) {

        int choice = 0;
        User user;
        do {
            try {

                System.out.printf("\n\n Bienvenido/a al banco %s\n\n", entity.getName());
                System.out.println("Seleccione una opción:");
                System.out.println("1) Abrir una cuenta nueva");
                System.out.println("2) Ingresar al cajero");
                System.out.println("3) Salir");
                choice = sc.nextInt();
                if (choice < 1 || choice > 3) {
                    System.out.println("Esa opción no está permitida eliga una opción entre [1-3]");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                user = Menu.createAccount(entity, sc);
                return user;
            case 2:
                user = Menu.loginView(entity, sc);
                return user;
            default:
                return null;
        }
    }

    public static User createAccount(Entity entity, Scanner sc) {

        String dni, email, firstName, lastName, pin, accountName;

        try {

            sc.nextLine();
            System.out.print("Ingrese su dni: ");
            dni = sc.nextLine();
            System.out.print("Ingrese su email: ");
            email = sc.nextLine();
            System.out.print("Ingrese su Nombre: ");
            firstName = sc.nextLine();
            System.out.print("Ingrese sus Apellidos: ");
            lastName = sc.nextLine();
            System.out.print("Ingrese su pin personal: ");
            pin = sc.nextLine();
            System.out.print("Ingrese un alias para su cuenta: ");
            accountName = sc.nextLine();

            User newUser = new User(dni, email, firstName, lastName, pin, entity);
            System.out.println("\nSu ID de usuario es: " + newUser.getUUID());

            Account newAccount = new Account(accountName, newUser, entity);
            entity.addAccount(newAccount);
            newUser.addAccount(newAccount);
            entity.addUser(newUser);
            return newUser;

        } catch (InputMismatchException e) {
            System.err.println(" ");
            System.err.println("|==Has introducido un valor no permitido            ==|");
            sc.nextLine();
        }
        return null;
    }

    public static User loginView(Entity entity, Scanner sc) {

        String userID, pin;
        int attempts = 0;
        User authUser = null;

        do {
            try {

                userID = sc.nextLine();
                System.out.print("\nIngrese su ID de usuario o dni: ");
                userID = sc.nextLine();
                System.out.print("Ingrese su pin: ");
                pin = sc.nextLine();

                authUser = entity.userLogin(userID, pin);
                if (authUser == null) {
                    System.out.println(
                            "Hay un error en su usuario o contraseña, verifique los datos e intentelo de nuevo");
                }
                attempts++;
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (authUser == null && attempts != 5);

        if (attempts == 5) {
            System.out.println("si no recuerda su usuario o pin\n"
                    + "llame a su banco para resolver la incidencia");
            return null;
        } else {
            return authUser;
        }
    }

    public static void userMenuView(ATMManagement atmMg, User user, Entity entity, Scanner sc) {

        int choice = 0;

        do {
            try {

                user.printAccountsSummary();
                System.out.printf("\n %s, que quiere hacer\n", user.getFirstName());
                System.out.println("   1) Ver Historial de Transacciones");
                System.out.println("   2) Añadir una nueva cuenta bancaria");
                System.out.println("   3) Retirar Dinero");
                System.out.println("   4) Depositar");
                System.out.println("   5) Realizar Transferencia");
                System.out.println("   6) Pagar servicios");
                System.out.println("   7) Salir");
                System.out.println("");
                System.out.print("Ingrese su elección: ");
                choice = sc.nextInt();

                if (choice < 1 || choice > 7) {
                    System.out.println("Esa no es una opción permitida, seleccione del 1-7");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (choice < 1 || choice > 7);

        switch (choice) {
            case 1:
                Menu.transHistoryView(user, sc);
                break;
            case 2:
                Menu.addAccount(user, entity, sc);
                break;
            case 3:
                Menu.withdrawlView(user, entity, sc);
                break;
            case 4:
                Menu.depositView(user, entity, sc);
                break;
            case 5:
                Menu.transferView(atmMg, user, entity, sc);
                break;
            case 6:
                Menu.payServices(atmMg, user, entity, sc);
                break;
            default:
                break;
        }

        if (choice != 7) {
            Menu.userMenuView(atmMg, user, entity, sc);
        }
    }

    public static void addAccount(User user, Entity entity, Scanner sc) {
        String accountName;

        try {

            sc.nextLine();
            System.out.print("Ingrese un alias para su cuenta: ");
            accountName = sc.nextLine();

            Account newAccount = new Account(accountName, user, entity);
            entity.addAccount(newAccount);
            user.addAccount(newAccount);
        } catch (InputMismatchException e) {
            System.err.println(" ");
            System.err.println("|==Has introducido un valor no permitido            ==|");
            sc.nextLine();
        }
    }

    public static void transHistoryView(User user, Scanner sc) {

        int choice = -1;

        do {
            try {

                System.out.printf("\nIngrese el numero (1-%d) de la cuenta\n de la que quiere ver las transacciones: ",
                        user.getNumAccounts());
                choice = sc.nextInt() - 1;

                if (choice < 0 || choice >= user.getNumAccounts()) {
                    System.out.println("Cuenta invalida seleccione de nuevo");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (choice < 0 || choice >= user.getNumAccounts());

        // Imprimir el histórico
        user.printAcctTransHistory(choice);
    }

    public static void transferView(ATMManagement atmMg, User user, Entity entity, Scanner sc) {
        int choice = 0;
        do {
            try {

                System.out.println("\nSeleccione una opción:");
                System.out.println("1) Transferencia entre cuentas personales");
                System.out.println("2) Transferencia interbancaria");
                System.out.println("3) Regresar");
                choice = sc.nextInt();
                if (choice < 1 || choice > 3) {
                    System.out.println("Esa opción no está permitida eliga una opción entre [1-3]");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==      Has introducido un valor no permitido      ==|");
                sc.nextLine();
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                Menu.personalTransferView(user, entity, sc);
                break;
            case 2:
                Menu.interbankTransferView(atmMg, user, entity, sc);
                break;
            default:
                break;
        }
    }

    public static void personalTransferView(User user, Entity entity, Scanner sc) {

        int fromAccount = -1, toAccount = -1;
        double amount = -1, acctBal;
        Transaction transaction;

        if (user.getNumAccounts() == 1) {
            System.out.println("Solo tiene una cuenta así que no puede realizar transferencias personales");
            return;
        }

        do {
            try {

                System.out.printf("\nIngrese el numero (1-%d) de la cuenta desde la que va a transferir: ",
                        user.getNumAccounts());
                fromAccount = sc.nextInt() - 1;

                if (fromAccount < 0 || fromAccount >= user.getNumAccounts()) {
                    System.out.println("Cuenta invalida seleccione de nuevo");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (fromAccount < 0 || fromAccount >= user.getNumAccounts());

        acctBal = user.getAcctBalance(fromAccount);

        do {
            try {
                System.out.printf("\nIngrese el numero (1-%d) de la cuenta a la que va a transferir: ",
                        user.getNumAccounts());
                toAccount = sc.nextInt() - 1;

                if (toAccount < 0 || toAccount >= user.getNumAccounts()) {
                    System.out.println("Cuenta inválida seleccione de nuevo");
                }
                if (toAccount == fromAccount) {
                    System.out.println("No puede transferirse a la misma cuenta desde la que va a transferir");
                    toAccount = -1;
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (toAccount < 0 || toAccount >= user.getNumAccounts() && toAccount != fromAccount);

        do {
            try {
                System.out.printf("\nIngrese el monto de la transferencia (max $%.02f): $", acctBal);
                amount = sc.nextDouble();
                if (amount < 0) {
                    System.out.println("El monto a transferir debe de ser mayor a 0");
                } else if (acctBal < amount) {
                    System.out.printf("El monto a transferir debe de ser menor que\n el balance que es de $%.02f.\n",
                            acctBal);
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (amount < 0 || acctBal < amount);
        transaction = new Transfer(amount, user.getAccount(fromAccount), user.getAccount(toAccount), entity,
                ETransactionTypes.personalTransfer);
        if (transaction.createTransaction()) {
            System.out.println("Transferencia realizada correctamente");
        } else {
            System.out.println("No se ha podido realizar la transferencia");
        }
    }

    public static void interbankTransferView(ATMManagement atmMg, User user, Entity entity, Scanner sc) {

        int fromAccount = -1;
        Account toAccount = null;
        String toAccountNumber = "";
        double amount = 0, acctBal;
        Transaction transaction;

        if (user.getNumAccounts() == 1) {
            fromAccount = 0;
        } else {
            do {
                try {
                    System.out.printf("\nIngrese el numero (1-%d) de la cuenta desde la que va a realizar el retiro: ",
                            user.getNumAccounts());
                    fromAccount = sc.nextInt() - 1;
                    if (fromAccount < 0 || fromAccount >= user.getNumAccounts()) {
                        System.out.println("Cuenta invalida seleccione de nuevo");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(" ");

                    System.err.println("|==Has introducido un valor no permitido            ==|");

                    sc.nextLine();
                }
            } while (fromAccount < 0 || fromAccount >= user.getNumAccounts());
        }

        acctBal = user.getAcctBalance(fromAccount);

        do {
            try {
                sc.nextLine();
                System.out.printf("Ingrese el numero único de la cuenta a la que va a transferir o 1 para regresar: ",
                        user.getNumAccounts());
                toAccountNumber = sc.nextLine();
                toAccount = atmMg.foundAccountInEntitiesForUUID(toAccountNumber);

                if (toAccountNumber.equals("1")) {
                    return;
                }

                if (toAccount == null) {
                    System.out.println("Esa cuenta no existe");
                }

            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (toAccount == null);

        do {
            try {
                System.out.printf("Ingrese el monto de la transferencia (max $%.02f): $", acctBal);
                amount = sc.nextDouble();
                if (amount < 0) {
                    System.out.println("El monto a transferir debe de ser mayor a 0");
                } else if (acctBal < amount) {
                    System.out.printf("El monto a transferir debe de ser menor que\n el balance que es de $%.02f.\n",
                            acctBal);
                }

            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (amount < 0 || acctBal < amount);
        transaction = new Transfer(amount, user.getAccount(fromAccount), toAccount, entity,
                ETransactionTypes.interbankTransfer);
        if (transaction.createTransaction()) {
            System.out.println("Transferencia realizada correctamente");
        } else {
            System.out.println("No se ha podido realizar la transferencia");
        }
    }

    public static void withdrawlView(User user, Entity entity, Scanner sc) {

        int fromAccount = -1;
        double amount = 0, accountBalance;
        Transaction transaction;

        if (user.getNumAccounts() == 1) {
            fromAccount = 0;
        } else {
            do {
                try {
                    System.out.printf("Ingrese el numero (1-%d) de la cuenta\n desde la que va a realizar el retiro: ",
                            user.getNumAccounts());
                    fromAccount = sc.nextInt() - 1;
                    if (fromAccount < 0 || fromAccount >= user.getNumAccounts()) {
                        System.out.println("Cuenta invalida seleccione de nuevo");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(" ");

                    System.err.println("|==Has introducido un valor no permitido            ==|");

                    sc.nextLine();
                }
            } while (fromAccount < 0 || fromAccount >= user.getNumAccounts());
        }

        accountBalance = user.getAcctBalance(fromAccount);

        do {
            try {
                System.out.printf("Ingrese el monto del retiro (max $%.02f): $", accountBalance);
                amount = sc.nextDouble();
                if (amount < 0) {
                    System.out.println("El monto a retirar debe de ser mayor a 0");
                } else if (accountBalance < amount) {
                    System.out.printf("El monto a retirar debe de ser menor que\n su saldo que es de $%.02f.\n",
                            accountBalance);
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (amount < 0 || accountBalance < amount);

        transaction = new Withdrawal(amount, user.getAccount(fromAccount), entity, ETransactionTypes.withdrawal);
        if (transaction.createTransaction()) {
            System.out.println("Retiro realizado correctamente");
        } else {
            System.out.println("No se ha podido realizar el retiro");
        }

    }

    public static void depositView(User user, Entity entity, Scanner sc) {

        int toAccount = -1;
        double amount = 0;
        Transaction transaction;

        if (user.getNumAccounts() == 1) {
            toAccount = 0;
        } else {
            do {
                try {
                    System.out.printf("Ingrese el numero (1-%d) de la cuenta\n En la que va a depositar: ",
                            user.getNumAccounts());
                    toAccount = sc.nextInt() - 1;

                    if (toAccount < 0 || toAccount >= user.getNumAccounts()) {
                        System.out.println("Cuenta invalida seleccione de nuevo");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(" ");

                    System.err.println("|==Has introducido un valor no permitido            ==|");

                    sc.nextLine();
                }
            } while (toAccount < 0 || toAccount >= user.getNumAccounts());
        }

        do {
            try {
                System.out.println("Ingrese el monto a depositar");
                amount = sc.nextDouble();
                if (amount < 0) {
                    System.out.println("El monto a depositar debe de ser mayor a 0");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (amount < 0);

        transaction = new Deposit(amount, user.getAccount(toAccount), entity, ETransactionTypes.deposit);
        if (transaction.createTransaction()) {
            System.out.println("Deposito realizado correctamente");
        } else {
            System.out.println("No se ha podido realizar el deposito");
        }
    }

    public static void payServices(ATMManagement atmMg, User user, Entity entity, Scanner sc) {
        int choice = 0;

        do {
            try {

                user.printAccountsSummary();
                System.out.printf("\nQue servicio desea pagar\n", user.getFirstName());
                System.out.println("   1) Agua");
                System.out.println("   2) Luz");
                System.out.println("   3) Gas");
                System.out.println("   4) Teléfono");
                System.out.println("   5) Salir");
                System.out.println("");
                System.out.print("Ingrese su elección: ");
                choice = sc.nextInt();

                if (choice < 1 || choice > 5) {
                    System.out.println("Esa no es una opción permitida, seleccione del 1-5");
                }
            } catch (InputMismatchException e) {
                System.err.println(" ");
                System.err.println("|==Has introducido un valor no permitido            ==|");
                sc.nextLine();
            }
        } while (choice < 1 || choice > 5);

        int toAccount = -1;

        if (user.getNumAccounts() == 1) {
            toAccount = 0;
        } else {
            do {
                try {
                    System.out.printf("Ingrese el numero (1-%d) de la cuenta\n Desde la que va a pagar: ",
                            user.getNumAccounts());
                    toAccount = sc.nextInt() - 1;

                    if (toAccount < 0 || toAccount >= user.getNumAccounts()) {
                        System.out.println("Cuenta invalida seleccione de nuevo");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(" ");

                    System.err.println("|==Has introducido un valor no permitido            ==|");

                    sc.nextLine();
                }
            } while (toAccount < 0 || toAccount >= user.getNumAccounts());
        }

        try {
            double amount;
            Transaction transaction = null;

            System.out.print("Cuanto desea pagar: ");
            amount = sc.nextDouble();

            switch (choice) {
                case 1:
                    transaction = new PayWater(amount, user.getAccount(toAccount), entity,
                            ETransactionTypes.servicesPayment);
                    break;
                case 2:
                    transaction = new PayLight(amount, user.getAccount(toAccount), entity,
                            ETransactionTypes.servicesPayment);
                    break;
                case 3:
                    transaction = new PayGas(amount, user.getAccount(toAccount), entity,
                            ETransactionTypes.servicesPayment);
                    break;
                case 4:
                    transaction = new PayPhone(amount, user.getAccount(toAccount), entity,
                            ETransactionTypes.servicesPayment);
                    break;
                default:
                    break;
            }
            if (choice >= 1 && choice < 5) {
                if (transaction.createTransaction()) {
                    System.out.println("Pago realizado correctamente");
                } else {
                    System.out.println("No se ha podido realizar el pago");
                }
            }

        } catch (InputMismatchException e) {
            System.err.println(" ");
            System.err.println("|==Has introducido un valor no permitido            ==|");
            sc.nextLine();
            Menu.payServices(atmMg, user, entity, sc);
        }
    }

}