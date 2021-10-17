package ilia.nemankov.repository;

import ilia.nemankov.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private final SessionFactory sessionFactory;

    public PersonRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM PERSON").list();
    }

    @Override
    public void save(Person person) {
        Session session = sessionFactory.openSession();
        Query<Person> query = session.createQuery("from PERSON where passportId = :passportIdValue");
        query.setParameter("passportIdValue", person.getPassportId());
        if (query.list().size() > 0) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Passport id in use").build());
        }

        Transaction transaction = session.beginTransaction();

        try {
            session.save(person);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Person findById(Long id) {
        Session session = sessionFactory.openSession();
        return session.get(Person.class, id);
    }

    @Override
    public Person update(Person newValue) {
        Session session = sessionFactory.openSession();
        session.update(newValue);
        return newValue;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            Person person = session.get(Person.class, id);
            if (person == null) {
                throw new NotFoundException(Response.status(HttpServletResponse.SC_NOT_FOUND).entity("Specified object not found").build());
            }

            session.createQuery("delete from ilia.nemankov.entity.Person where id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
