package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    List<PersonDTO> findAll();

    PersonDTO save(PersonDTO dto);

    PersonDTO findById(Long id);

    void delete(Long id);

}
