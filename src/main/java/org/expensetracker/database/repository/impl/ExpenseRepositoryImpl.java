package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.repository.ExpenseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public static void main(String[] args) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();


        Account account = new AccountRepositoryImpl(configuration.buildSessionFactory()).findById(2L).get();
        Category category = new CategoryRepositoryImpl(configuration.buildSessionFactory()).findById(2L).get();

        ExpenseRepository repository = new ExpenseRepositoryImpl(configuration.buildSessionFactory());

        Expense expense = new Expense(BigDecimal.valueOf(1700), LocalDate.now(), category, account);

        //add - works
        //System.out.println(repository.add(expense));

        //find all - works
        //System.out.println(repository.findAll());
        //find by id - work
        System.out.println(repository.findById(1L).get().getCategory());
        //update - works
        //System.out.println(repository.updateById(2L, expense));
        //delete - works
        //System.out.println(repository.deleteById(2l));
    }
}
