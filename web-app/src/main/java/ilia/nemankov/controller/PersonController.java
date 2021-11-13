package ilia.nemankov.controller;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.service.BadResponseException;
import ilia.nemankov.service.PersonService;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/persons")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class PersonController {

    private final PersonService personService;

    public PersonController() throws NamingException {
        Context context = new ContextProviderImpl().getContext();
        Object ref = context.lookup("java:global/pool/PersonServiceImpl");
        this.personService = (PersonService) PortableRemoteObject.narrow(ref, PersonService.class);
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        PersonDTO person = personService.findById(id);

        if (person != null) {
            return Response.status(HttpServletResponse.SC_OK).entity(person).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @GET
    @Path("")
    public Response getPersons() {
        List<PersonDTO> persons = personService.findAll();

        if (persons.size() > 0) {
            return Response.status(HttpServletResponse.SC_OK).entity(persons).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @POST
    @Path("")
    public Response addPerson(PersonDTO personDTO) {
        try {
            PersonDTO savedValue = personService.save(personDTO);
            return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
        } catch(BadResponseException e) {
            return Response.status(e.getResponseCode()).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoordinates(@PathParam("id") Long id) {
        try {
            personService.delete(id);
            return Response.status(HttpServletResponse.SC_OK).build();
        } catch(
        BadResponseException e) {
            return Response.status(e.getResponseCode()).entity(e.getMessage()).build();
        }
    }
}
