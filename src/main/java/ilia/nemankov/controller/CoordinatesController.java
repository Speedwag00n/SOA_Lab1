package ilia.nemankov.controller;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.service.CoordinatesService;
import ilia.nemankov.service.CoordinatesServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/coordinates")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class CoordinatesController extends HttpServlet {

    private final CoordinatesService coordinatesService;

    public CoordinatesController() {
        this.coordinatesService = new CoordinatesServiceImpl();
    }

    @GET
    @Path("/{id}")
    public Response getCoordinates(@PathParam("id") Long id) {
        CoordinatesDTO coordinate = coordinatesService.findById(id);

        if (coordinate != null) {
            return Response.status(HttpServletResponse.SC_OK).entity(coordinate).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @GET
    @Path("")
    public Response getCoordinates() {
        List<CoordinatesDTO> coordinates = coordinatesService.findAll();

        if (coordinates.size() > 0) {
            return Response.status(HttpServletResponse.SC_OK).entity(coordinates).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @POST
    @Path("")
    public Response addCoordinates(CoordinatesDTO coordinate) {
        CoordinatesDTO savedValue = coordinatesService.save(coordinate);
        return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoordinates(@PathParam("id") Long id) {
        coordinatesService.delete(id);
        return Response.status(HttpServletResponse.SC_OK).build();
    }
}
