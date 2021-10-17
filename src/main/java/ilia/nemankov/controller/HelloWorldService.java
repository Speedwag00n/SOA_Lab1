package ilia.nemankov.controller;

import javax.ws.rs.*;
@Path("/library")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class HelloWorldService {
    @GET
    @Path("/book/{isbn}")
    public String getBook(@PathParam("isbn") String id) {
        return "a";
    }
}
