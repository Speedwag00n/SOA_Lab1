package ilia.nemankov.repository;

import ilia.nemankov.entity.Coordinates;
import ilia.nemankov.service.BadResponseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CoordinatesRepositoryImpl implements CoordinatesRepository {

    private final SessionFactory sessionFactory;

    public CoordinatesRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<Coordinates> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM COORDINATES").list();
    }

    @Override
    public void save(Coordinates coordinates) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(coordinates);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Coordinates findById(Long id) {
        Session session = sessionFactory.openSession();
        return session.get(Coordinates.class, id);
    }

    @Override
    public Coordinates update(Coordinates newValue) {
        Session session = sessionFactory.openSession();
        session.update(newValue);
        return newValue;
    }

    @Override
    public void delete(Long id) throws BadResponseException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Coordinates coordinates = session.get(Coordinates.class, id);
            if (coordinates == null) {
                throw new BadResponseException("Specified object not found", HttpServletResponse.SC_NOT_FOUND);
            }

            session.createQuery("delete from ilia.nemankov.entity.Coordinates where id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
