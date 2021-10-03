package ilia.nemankov.repository;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.entity.MPAARating;
import ilia.nemankov.entity.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {

    private final SessionFactory sessionFactory;

    public MovieRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<Movie> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM MOVIE").list();
    }

    @Override
    public List<Movie> findAll(FilterConfiguration filterConfiguration) throws ParseException {
        Session session = sessionFactory.openSession();
        List<Movie> result;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> criteria = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteria.from(Movie.class);

        if (filterConfiguration.getOrder() != null) {
            List<Order> orders = new ArrayList<>();

            for (String order : filterConfiguration.getOrder()) {
                String[] parts = order.split(",");
                if (parts.length == 2) {
                    if (parts[1].equals("d")) {
                        orders.add(criteriaBuilder.desc(root.get(parts[0])));
                    } else if (parts[1].equals("a")) {
                        orders.add(criteriaBuilder.asc(root.get(parts[0])));
                    }
                }
            }

            criteria.orderBy(orders);
        }

        if (filterConfiguration.getFilter() != null) {
            List<Predicate> predicates = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            for (String filter : filterConfiguration.getFilter()) {
                String[] parts = filter.split(",");

                if (parts.length == 3) {
                    switch (parts[1]) {
                        case "==":
                            if (parts[0].equals("creationDate")) {
                                predicates.add(criteriaBuilder.equal(root.<Date>get(parts[0]), formatter.parse(parts[2])));
                            } else if (parts[0].equals("coordinates") || parts[0].equals("screenWriter")) {
                                if (parts[0].equals("screenWriter") && parts[2].equals("None")) {
                                    predicates.add(criteriaBuilder.isNull(root.get(parts[0]).get("id")));
                                } else {
                                    predicates.add(criteriaBuilder.equal(root.get(parts[0]).get("id"), parts[2]));
                                }
                            } else if (parts[0].equals("mpaaRating")) {
                                predicates.add(criteriaBuilder.equal(root.get(parts[0]), MPAARating.valueOf(parts[2])));
                            } else {
                                predicates.add(criteriaBuilder.equal(root.get(parts[0]), parts[2]));
                            }
                            break;
                        case "<=":
                            if (parts[0].equals("creationDate")) {
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parts[0]), formatter.parse(parts[2])));
                            } else {
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parts[0]), parts[2]));
                            }
                            break;
                        case ">=":
                            if (parts[0].equals("creationDate")) {
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parts[0]), formatter.parse(parts[2])));
                            } else {
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parts[0]), parts[2]));
                            }
                            break;
                        case "<":
                            if (parts[0].equals("creationDate")) {
                                predicates.add(criteriaBuilder.lessThan(root.get(parts[0]), formatter.parse(parts[2])));
                            } else {
                                predicates.add(criteriaBuilder.lessThan(root.get(parts[0]), parts[2]));
                            }
                            break;
                        case ">":
                            if (parts[0].equals("creationDate")) {
                                predicates.add(criteriaBuilder.greaterThan(root.get(parts[0]), formatter.parse(parts[2])));
                            } else {
                                predicates.add(criteriaBuilder.greaterThan(root.get(parts[0]), parts[2]));
                            }
                            break;
                        case "contains":
                            predicates.add(criteriaBuilder.like(root.get(parts[0]), "%" + parts[2] + "%"));
                            break;
                    }
                }
            }

            criteria.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        Query<Movie> query = session.createQuery(criteria);

        if (filterConfiguration.getCount() != null && filterConfiguration.getPage() != null) {
            query.setMaxResults(filterConfiguration.getCount());
            query.setFirstResult(filterConfiguration.getCount() * (filterConfiguration.getPage() - 1));
        }

        result = query.list();

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
        }
    }

    @Override
    public Movie findById(Long id) {
        Session session = sessionFactory.openSession();
        return session.get(Movie.class, id);
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
        }
    }
}
