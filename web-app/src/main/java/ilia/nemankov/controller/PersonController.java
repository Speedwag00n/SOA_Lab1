package ilia.nemankov.controller;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.service.BadResponseException;
import ilia.nemankov.service.PersonService;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
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
    public PersonDTO getPerson(@WebParam(name = "id") Long id) throws BadResponseException {
        PersonDTO value = personService.findById(id);

        if (value != null) {
            return value;
        } else {
            throw new BadResponseException("Not found", 404);
        }
    }

    @WebMethod
    public List<PersonDTO> getPersons() {
        return personService.findAll();
    }

    @WebMethod
    public PersonDTO addPerson(@WebParam(name = "person") PersonDTO personDTO) throws BadResponseException {
        return personService.save(personDTO);
    }

    @WebMethod
    public boolean deletePerson(@WebParam(name = "deleteId") Long id) throws BadResponseException {
        personService.delete(id);
        return true;
    }
}
