package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.utils.InvalidValueException;

import java.util.List;

public interface PersonService {

    List<PersonDTO> findAll();

    PersonDTO save(PersonDTO dto) throws InvalidValueException;

    PersonDTO findById(Long id);

    void delete(Long id);

}
