package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserServices;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserServices userServices = new UserDaoJDBCImpl();
        userServices.createUsersTable();

        userServices.saveUser("Name1", "LastName1", (byte) 20);
        userServices.saveUser("Name2", "LastName2", (byte) 25);
        userServices.saveUser("Name3", "LastName3", (byte) 31);
        userServices.saveUser("Name4", "LastName4", (byte) 38);

        userServices.removeUserById(1);
        System.out.println(userServices.getAllUsers());
        userServices.cleanUsersTable();
        userServices.dropUsersTable();
    }
}
