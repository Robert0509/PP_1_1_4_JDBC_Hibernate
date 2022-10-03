package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT, name TINYTEXT NOT NULL, lastName TINYTEXT NOT NULL, age TINYINT, PRIMARY KEY(id))";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании таблицы");
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении таблицы");
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при добавлении пользователя");
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении пользователя");
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        String sql = "from User";

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery(sql).list();
            session.getTransaction().commit();

            return list;
        } catch (HibernateException e) {
            System.out.println("Ошибка при возвращении всех пользователей");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE  users";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при очистки таблицы");
            e.printStackTrace();

        }
    }
}
