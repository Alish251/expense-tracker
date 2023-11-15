package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.repository.ExpenseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final SessionFactory sessionFactory;

    public ExpenseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Optional<List<Expense>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Expense> query = session.createQuery("FROM Expense", Expense.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<Expense> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Expense.class, id));
        }
    }

    @Override
    public Optional<Expense> add(Expense expense) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(expense);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(expense);
    }

    @Override
    public Optional<Expense> updateById(Long id, Expense expenseUpdated) {
        Transaction transaction = null;
        Expense expenseToBeUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            expenseToBeUpdated = session.get(Expense.class, id);

            expenseToBeUpdated.setAmount(expenseUpdated.getAmount());
            expenseToBeUpdated.setCategory(expenseUpdated.getCategory());
            expenseToBeUpdated.setDate(expenseUpdated.getDate());
            expenseToBeUpdated.setAccount(expenseUpdated.getAccount());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(expenseToBeUpdated);
    }

    @Override
    public Optional<Long> deleteById(Long id) {
        Transaction transaction = null;
        Expense expenseToDelete;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            expenseToDelete = session.get(Expense.class, id);
            if (expenseToDelete != null) {
                session.delete(expenseToDelete);
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
