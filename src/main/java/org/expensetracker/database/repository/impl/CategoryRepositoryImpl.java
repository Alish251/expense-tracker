package org.expensetracker.database.repository.impl;

import org.expensetracker.database.entity.Category;
import org.expensetracker.database.repository.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SessionFactory sessionFactory;

    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Category>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Category> query = session.createQuery("from Category", Category.class);
            return Optional.ofNullable(query.getResultList());
        }
    }

    @Override
    public Optional<Category> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Category.class, id));
        }
    }

    @Override
    public Optional<Category> add(Category category) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(category);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> updateById(@NotNull Long id, Category categoryUpdated) {
        Transaction transaction = null;
        Category categoryToBeUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            categoryToBeUpdated = session.get(Category.class, id);

            categoryToBeUpdated.setDescription(categoryUpdated.getDescription());
            categoryToBeUpdated.setName(categoryUpdated.getName());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.of(categoryToBeUpdated);
    }

    @Override
    public Optional<Long> deleteById(Long id) {
        Transaction transaction = null;
        Category categoryToDelete = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            categoryToDelete = session.get(Category.class, id);
            if (categoryToDelete != null) {
                session.delete(categoryToDelete);
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
