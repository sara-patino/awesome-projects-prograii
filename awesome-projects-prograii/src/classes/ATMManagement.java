package classes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import classes.transaction.Transaction;
import common.Files;
import common.Utils;
import interfaces.IManagement;

public class ATMManagement implements IManagement {

    ArrayList<Entity> entities;
    ArrayList<Account> accounts;
    ArrayList<User> users;
    ArrayList<Transaction> transactions;

    /**
     * Constructor base
     */
    public ATMManagement() {
        entities = new ArrayList<Entity>();
    }

    /**
     * M�todo para cargar la Data
     */
    private void loadData() {
        List<String> listEntities;

        try {
            listEntities = Files.loadFile("data/Entities.dat");

            Entity entity;
            String entityName;


            for (String lineEntities : listEntities) {

                // Separamos la data
                String[] lineEntitiesData = lineEntities.split(",");
                // Le quitamos los caracteres " de cada cadena
                lineEntitiesData = Utils.clearString(lineEntitiesData, "\"", "");

                // Controlar si los datos existen y si son el tipo de dato adecuado
                entityName = lineEntitiesData[0];


                entity = new Entity(entityName);

                entities.add(entity);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * M�todo para inicializar programa
     */
    public void start() {
        this.loadData();
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public Account foundAccountInEntitiesForUUID(String UUID) {

        for(Entity e: this.entities) {
            for(Account a: e.getAccounts()) {
                if(a.getUUID().equals(UUID)) {
                    return a;
                }
            }
        }

        return null;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }
}
