package interfaces;

import java.util.List;

import classes.Account;
import classes.Entity;

public interface IManagement {
    void start();

    void addEntity(Entity e);

    Account foundAccountInEntitiesForUUID(String UUID);

    List<Entity> getEntities();
}