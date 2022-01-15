package ilia.nemankov.controller;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.service.BadResponseException;
import ilia.nemankov.service.PersonService;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.util.List;

@Stateless
@WebService(
        targetNamespace = "http://localhost:8090/ws/person"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class PersonController {

    private final PersonService personService;

    public PersonController() throws NamingException {
        Context context = new ContextProviderImpl().getContext();
        Object ref = context.lookup("pool/PersonServiceImpl!ilia.nemankov.service.PersonService");
        this.personService = (PersonService) PortableRemoteObject.narrow(ref, PersonService.class);
    }

    @WebMethod
    public PersonDTO getPerson(Long id) {
        return personService.findById(id);
    }

    @WebMethod
    public List<PersonDTO> getPersons() {
        return personService.findAll();
    }

    @WebMethod
    public boolean addPerson(PersonDTO personDTO) throws BadResponseException {
        PersonDTO savedValue = personService.save(personDTO);
        return true;
    }

    @WebMethod
    public boolean deleteCoordinates(Long id) throws BadResponseException {
        personService.delete(id);
        return true;
    }
}
