package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PersonService {

    List<PersonDTO> findAll();

    PersonDTO save(PersonDTO dto) throws BadResponseException;

    PersonDTO findById(Long id);

    void delete(Long id) throws BadResponseException;

}
