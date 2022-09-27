package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, name TINYTEXT NOT NULL, lastName TINYTEXT NOT NULL, age TINYINT )";
       try( Connection con = Util.getConnection();Statement statement = con.createStatement()){
           statement.executeUpdate(query);
       }
       catch (SQLException e) {
           System.err.println("Ошибка при создании таблицы");
           e.getErrorCode();
       }


    }

    public void dropUsersTable() {
        String query= "DROP TABLE IF EXISTS  user";
        try(Connection con = Util.getConnection(); Statement statement = con.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
            e.getErrorCode();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection con = Util.getConnection(); PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,name);
            ps.setString(2,lastName);
            ps.setByte(3,age);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (Connection con = Util.getConnection(); PreparedStatement ps = con.prepareStatement(query)){
            ps.setLong(1, id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        String query ="SELECT * FROM user";
        List<User> list = new ArrayList<>();
        try( Connection con = Util.getConnection();Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age =resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void cleanUsersTable() {
        String query = "TURNCATE TABLE user";
        try( Connection con = Util.getConnection();Statement statement = con.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
