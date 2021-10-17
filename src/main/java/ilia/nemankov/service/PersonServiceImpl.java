package ilia.nemankov.service;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Person;
import ilia.nemankov.mapper.PersonMapper;
import ilia.nemankov.repository.PersonRepository;
import ilia.nemankov.repository.PersonRepositoryImpl;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
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
    public PersonDTO save(PersonDTO dto) {
        validate(dto);
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

    private void validate(PersonDTO dto) {
        if (dto == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Request body must be specified").build());
        }

        if (dto.getName() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'name' must be specified").build());
        }

        if (dto.getHeight() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'height' must be specified").build());
        }

        if (dto.getPassportId() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'passportId' must be specified").build());
        }

        if (dto.getNationality() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'nationality' must be specified").build());
        }

        if (!(dto.getName().length() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Length of field 'name' must be bigger than 0").build());
        }

        if (!(dto.getHeight() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'height' must be bigger than 0").build());
        }

        if (!(dto.getPassportId().length() < 32) || !(dto.getPassportId().length() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Length of field 'passportId' must be bigger than 0 and less than 32").build());
        }
    }
}
