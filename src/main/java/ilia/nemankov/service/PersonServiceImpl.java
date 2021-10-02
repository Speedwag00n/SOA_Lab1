package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Person;
import ilia.nemankov.mapper.PersonMapper;
import ilia.nemankov.repository.PersonRepository;
import ilia.nemankov.repository.PersonRepositoryImpl;
import ilia.nemankov.utils.InvalidValueException;

import java.util.ArrayList;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonServiceImpl() {
        this.personRepository = new PersonRepositoryImpl();
        this.personMapper = new PersonMapper();
    }

    @Override
    public List<PersonDTO> findAll() {
        List<Person> persons =  personRepository.findAll();

        List<PersonDTO> result = new ArrayList<>();

        for (Person person : persons) {
            result.add(personMapper.entityToDto(person));
        }

        return result;
    }

    @Override
    public PersonDTO save(PersonDTO dto) throws InvalidValueException {
        Person person = personMapper.dtoToEntity(dto);

        personRepository.save(person);
        return personMapper.entityToDto(person);
    }

    @Override
    public PersonDTO findById(Long id) {
        Person person = personRepository.findById(id);

        if (person != null) {
            return personMapper.entityToDto(person);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        personRepository.delete(id);
    }
}
