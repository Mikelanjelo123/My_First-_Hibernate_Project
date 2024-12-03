package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl  implements UserDao {
   private final Connection connection = Util.getDBConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createUsersTableSQL = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50),
                lastName VARCHAR(50),
                age TINYINT
                );
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsersTableSQL);
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertUserSQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void removeUserById(long id) {
        String deleteUserSQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public List<User> getAllUsers() {
        String selectAllUsersSQL = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(selectAllUsersSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {

        }
        return users;
    }

    public void cleanUsersTable() {
        String truncateUsersTableSQL = "TRUNCATE TABLE users";
        try (PreparedStatement statement = connection.prepareStatement(truncateUsersTableSQL)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
