package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    List<PersonDTO> findAll();

}
