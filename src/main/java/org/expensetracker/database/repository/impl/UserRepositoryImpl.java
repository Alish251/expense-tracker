package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;


    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<User>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    @Override
    public Optional<User> add(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> updateById(Long id, User userUpdateTo) {
        Transaction transaction = null;
        User entity = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            entity = session.get(User.class, id);

            entity.setFirstname(userUpdateTo.getFirstname());
            entity.setLastname(userUpdateTo.getLastname());
            entity.setEmail(userUpdateTo.getEmail());
            entity.setAccounts(userUpdateTo.getAccounts());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<Long> deleteById(Long id) {
        Transaction transaction = null;
        User userToDelete;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userToDelete = session.get(User.class, id);
            if (userToDelete != null) {
                session.delete(userToDelete);
            }
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.of(id);
    }
}
