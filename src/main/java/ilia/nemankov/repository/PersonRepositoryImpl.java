package ilia.nemankov.repository;

import ilia.nemankov.entity.Person;
import ilia.nemankov.filters.MovieFilter;
import ilia.nemankov.filters.PersonFilter;
import ilia.nemankov.utils.Error;
import ilia.nemankov.utils.InvalidValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private final SessionFactory sessionFactory;

    public PersonRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Person> result;

        try {
            result = session.createQuery("FROM PERSON").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }

        return result;
    }

    @Override
    public void save(Person person) throws InvalidValueException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query<Person> query = session.createQuery("from PERSON where passportId = :passportIdValue");
            query.setParameter("passportIdValue", person.getPassportId());
            if (query.list().size() > 0) {
                throw new InvalidValueException(new Error(PersonFilter.PASSPORT_ID_IN_USE, "Passport id in use"));
            }

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
        Transaction transaction = session.beginTransaction();

        Person result = null;

        try {
            result = session.get(Person.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        return result;
    }

    @Override
    public Person update(Person newValue) {
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
            session.createQuery("delete from ilia.nemankov.entity.Person where id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
