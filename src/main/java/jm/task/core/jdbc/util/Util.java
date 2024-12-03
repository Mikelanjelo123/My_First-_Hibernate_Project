package jm.task.core.jdbc.util;

import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/kata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";


    static public Connection getDBConnection() {
        try {
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DB_DRIVER)
                    .setProperty("hibernate.connection.url", DB_CONNECTION)
                    .setProperty("hibernate.connection.username", DB_USER)
                    .setProperty("hibernate.connection.password", DB_PASSWORD)
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.show_sql", "false")
                    .addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            System.out.println("Connected to database");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create session factory", e);
        }
        return sessionFactory;
    }
}
