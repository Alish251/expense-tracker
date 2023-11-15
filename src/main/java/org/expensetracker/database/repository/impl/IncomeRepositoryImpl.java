package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.Income;
import org.expensetracker.database.repository.IncomeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
}
