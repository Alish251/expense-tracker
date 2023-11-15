package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.repository.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SessionFactory sessionFactory;

    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Account>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Account> query = session.createQuery("FROM Account", Account.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Account.class, id));
        }
    }

    @Override
    public Optional<Account> add(Account account) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            exception.printStackTrace();
        }
        return Optional.ofNullable(account);
    }

    @Override
    public Optional<Account> updateById(Long id, Account accountUpdated) {
        Transaction transaction = null;
        Account accountToBeUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            accountToBeUpdated = session.get(Account.class, id);

            accountToBeUpdated.setBalance(accountUpdated.getBalance());
            accountToBeUpdated.setUser(accountUpdated.getUser());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(accountToBeUpdated);
    }

    @Override
    public Optional<Long> deleteById(Long id) {
        Transaction transaction = null;
        Account accountToDelete;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            accountToDelete = session.get(Account.class, id);
            if (accountToDelete != null) {
                session.delete(accountToDelete);
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
