package ilia.nemankov.controller;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.service.BadResponseException;
import ilia.nemankov.service.CoordinatesService;
import ilia.nemankov.utils.Utils;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/coordinates")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class CoordinatesController {

    private final CoordinatesService coordinatesService;

    public CoordinatesController()throws NamingException {
        Context context = new ContextProviderImpl().getContext();
        Object ref = context.lookup("java:global/pool/CoordinatesServiceImpl");
        this.coordinatesService = (CoordinatesService) PortableRemoteObject.narrow(ref, CoordinatesService.class);
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
    public Response addCoordinates(CoordinatesDTO coordinate) throws Exception {
        try {
            CoordinatesDTO savedValue = coordinatesService.save(coordinate);
            return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
        } catch (Exception e) {
            BadResponseException badResponseException = Utils.deserializeBadResponseException(e);
            return Response.status(badResponseException.getResponseCode()).entity(badResponseException.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoordinates(@PathParam("id") Long id) throws Exception {
        try {
            coordinatesService.delete(id);
            return Response.status(HttpServletResponse.SC_OK).build();
        } catch (Exception e) {
            BadResponseException badResponseException = Utils.deserializeBadResponseException(e);
            return Response.status(badResponseException.getResponseCode()).entity(badResponseException.getMessage()).build();
        }
    }
}
