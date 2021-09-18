package ilia.nemankov.repository;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.entity.Movie;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.OrderImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Queue;

public class MovieRepositoryImpl implements MovieRepository {

    private final SessionFactory sessionFactory;

    public MovieRepositoryImpl() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public List<Movie> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Movie> result;

        try {
            result = session.createQuery("FROM MOVIE").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }

        return result;
    }

    @Override
    public List<Movie> findAll(FilterConfiguration filterConfiguration) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Movie> result;

        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Movie> criteria = criteriaBuilder.createQuery(Movie.class);
            Root<Movie> root = criteria.from(Movie.class);

            if (filterConfiguration.getOrder() != null) {
                for (String order : filterConfiguration.getOrder()) {
                    String[] parts = order.split(",");
                    if (parts.length == 2) {
                        if (parts[1].equals("d")) {
                            criteria.orderBy(criteriaBuilder.desc(root.get(parts[0])));
                        } else if (parts[1].equals("a")) {
                            criteria.orderBy(criteriaBuilder.asc(root.get(parts[0])));
                        }
                    }
                }
            }

            if (filterConfiguration.getFilter() != null) {
                for (String filter : filterConfiguration.getFilter()) {
                    String[] parts = filter.split(",");

                    if (parts.length == 3) {
                        switch (parts[1]) {
                            case "==":
                                criteria.where(criteriaBuilder.equal(root.get(parts[0]), parts[2]));
                                break;
                            case "<=":
                                criteria.where(criteriaBuilder.lessThanOrEqualTo(root.get(parts[0]), parts[2]));
                                break;
                            case ">=":
                                criteria.where(criteriaBuilder.greaterThanOrEqualTo(root.get(parts[0]), parts[2]));
                                break;
                            case "<":
                                criteria.where(criteriaBuilder.lessThan(root.get(parts[0]), parts[2]));
                                break;
                            case ">":
                                criteria.where(criteriaBuilder.greaterThan(root.get(parts[0]), parts[2]));
                                break;
                            case "contains":
                                criteria.where(criteriaBuilder.like(root.get(parts[0]), "%" + parts[2] + "%"));
                                break;
                        }
                    }
                }
            }

            Query<Movie> query = session.createQuery(criteria);

            if (filterConfiguration.getCount() != null && filterConfiguration.getPage() != null) {
                query.setMaxResults(filterConfiguration.getCount());
                query.setFirstResult(filterConfiguration.getCount() * (filterConfiguration.getPage() - 1));
            }

            result = query.list();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }

        return result;
    }

    @Override
    public void save(Movie movie) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(movie);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Movie findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Movie result = null;

        try {
            result = session.get(Movie.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        return result;
    }

    @Override
    public Movie update(Movie newValue) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(newValue);
            transaction.commit();
            return newValue;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.createQuery("delete from ilia.nemankov.entity.Movie where id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
