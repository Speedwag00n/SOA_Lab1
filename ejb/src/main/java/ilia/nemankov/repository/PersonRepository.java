package ilia.nemankov.repository;

import ilia.nemankov.entity.Person;
import ilia.nemankov.service.BadResponseException;

import java.util.List;

public interface PersonRepository {
    List<Person> findAll();

    void save(Person coordinates) throws BadResponseException;

    Person findById(Long id);

    Person update(Person newValue);

    void delete(Long id) throws BadResponseException;
}
