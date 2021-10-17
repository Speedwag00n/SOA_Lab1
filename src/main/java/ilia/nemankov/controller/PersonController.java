package ilia.nemankov.controller;

import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.service.PersonService;
import ilia.nemankov.service.PersonServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/persons")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class PersonController extends HttpServlet {

    private final PersonService personService;

    public PersonController() {
        this.personService = new PersonServiceImpl();
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
        PersonDTO savedValue = personService.save(personDTO);
        return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoordinates(@PathParam("id") Long id) {
        personService.delete(id);
        return Response.status(HttpServletResponse.SC_OK).build();
    }
}
