package ilia.nemankov.repository;

import ilia.nemankov.entity.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> findAll();

    void save(Person coordinates);

    Person findById(Long id);

    Person update(Person newValue);

    void delete(Long id);
}
