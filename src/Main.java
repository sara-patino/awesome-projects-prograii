import classes.Entity;
import classes.User;
import client.Menu;

import java.util.Scanner;

import classes.ATMManagement;;


public class Main {

    public static void main (String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            ATMManagement atmMg = new ATMManagement();
            atmMg.start();


            User user = null;
            Entity entity;
            while(true) {
                //permanecer en el men� hasta ingresar correctamente
                entity = Menu.ATMMainView(atmMg,sc);

                if (entity != null) user = Menu.bankMainView(entity,sc);



                if(user != null) Menu.userMenuView(atmMg,user, entity,sc);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
