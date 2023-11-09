package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.Income;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.IncomeRepository;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class IncomeRepositoryImpl implements IncomeRepository {
    private final SessionFactory sessionFactory;

    public IncomeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Income>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Income> query = session.createQuery("FROM Income", Income.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<Income> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Income.class, id));
        }
    }

    @Override
    public Optional<Income> add(Income income) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(income);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(income);
    }

    @Override
    public Optional<Income> updateById(Long id, Income incomeUpdated) {
        Transaction transaction = null;
        Income incomeToBeUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            incomeToBeUpdated = session.get(Income.class, id);

            incomeToBeUpdated.setAmount(incomeUpdated.getAmount());
            incomeToBeUpdated.setIncomeSource(incomeUpdated.getIncomeSource());
            incomeToBeUpdated.setDate(incomeUpdated.getDate());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(incomeToBeUpdated);
    }

    @Override
    public Optional<Long> deleteById(Long id) {
        Transaction transaction = null;
        Income incomeToDelete;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            incomeToDelete = session.get(Income.class, id);
            if (incomeToDelete != null) {
                session.delete(incomeToDelete);
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

        IncomeRepository repository = new IncomeRepositoryImpl(configuration.buildSessionFactory());

        IncomeSourceRepository incomeSourceRepository = new IncomeSourceRepositoryImpl(configuration.buildSessionFactory());
        AccountRepository accountRepository = new AccountRepositoryImpl(configuration.buildSessionFactory());

        Income income = new Income(BigDecimal.valueOf(27800.85), LocalDate.now(), incomeSourceRepository.findById(2L).get(), accountRepository.findById(2L).get());
        //add - works
        //System.out.println(repository.add(income));

        //find all - works
        //System.out.println(repository.findAll());
        //find by id - works
        //System.out.println(repository.findById(1L));
        //update - works
        //System.out.println(repository.updateById(1L, income));
        //delete - works
        //System.out.println(repository.deleteById(2l));
    }
}
