package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Person;
import ilia.nemankov.mapper.PersonMapper;
import ilia.nemankov.repository.PersonRepository;
import ilia.nemankov.repository.PersonRepositoryImpl;
import ilia.nemankov.utils.Utils;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Stateless
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
    public PersonDTO save(PersonDTO dto) throws Exception {
        try {
            validate(dto);
            Person person = personMapper.dtoToEntity(dto);

            personRepository.save(person);
            return personMapper.entityToDto(person);
        } catch (BadResponseException e) {
            throw Utils.serializeBadResponseException(e);
        }
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
    public void delete(Long id) throws Exception {
        try {
            personRepository.delete(id);
        } catch (BadResponseException e) {
            throw Utils.serializeBadResponseException(e);
        }
    }

    private void validate(PersonDTO dto) throws BadResponseException {
        if (dto == null) {
            throw new BadResponseException("Request body must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (dto.getName() == null) {
            throw new BadResponseException("Field 'name' must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (dto.getHeight() == null) {
            throw new BadResponseException("Field 'height' must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (dto.getPassportId() == null) {
            throw new BadResponseException("Field 'passportId' must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (dto.getNationality() == null) {
            throw new BadResponseException("Field 'nationality' must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!(dto.getName().length() > 0)) {
            throw new BadResponseException("Length of field 'name' must be bigger than 0", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!(dto.getHeight() > 0)) {
            throw new BadResponseException("Field 'height' must be bigger than 0", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!(dto.getPassportId().length() < 32) || !(dto.getPassportId().length() > 0)) {
            throw new BadResponseException("Length of field 'passportId' must be bigger than 0 and less than 32", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
