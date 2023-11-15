package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IncomeSourceRepositoryImpl implements IncomeSourceRepository {
    private final SessionFactory sessionFactory;

    public IncomeSourceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<IncomeSource>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<IncomeSource> query = session.createQuery("from IncomeSource", IncomeSource.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<IncomeSource> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(IncomeSource.class, id));
        }
    }

    @Override
    public Optional<IncomeSource> add(IncomeSource incomeSource) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(incomeSource);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(incomeSource);
    }

    @Override
    public Optional<IncomeSource> updateById(Long id, IncomeSource incomeSourceUpdated) {
        Transaction transaction = null;
        IncomeSource incomeSourceToBeUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            incomeSourceToBeUpdated = session.get(IncomeSource.class, id);
            incomeSourceToBeUpdated.setDescription(incomeSourceUpdated.getDescription());
            incomeSourceToBeUpdated.setName(incomeSourceUpdated.getName());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(incomeSourceToBeUpdated);
    }

    @Override
    public Optional<Long> deleteById(Long id) {
        Transaction transaction = null;
        IncomeSource incomeSourceToDelete = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            incomeSourceToDelete = session.get(IncomeSource.class, id);
            if (incomeSourceToDelete != null) {
                session.delete(incomeSourceToDelete);
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
