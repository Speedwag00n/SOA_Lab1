package ilia.nemankov.mapper;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Person;

public class PersonMapper {

    public Person dtoToEntity(PersonDTO dto) {
        Person person = new Person();

        person.setId(dto.getId());
        person.setName(dto.getName());
        person.setHeight(dto.getHeight());
        person.setPassportId(dto.getPassportId());
        person.setNationality(dto.getNationality());

        return person;
    }

    public PersonDTO entityToDto(Person entity) {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setId(entity.getId());
        personDTO.setName(entity.getName());
        personDTO.setHeight(entity.getHeight());
        personDTO.setPassportId(entity.getPassportId());
        personDTO.setNationality(entity.getNationality());

        return personDTO;
    }

}
