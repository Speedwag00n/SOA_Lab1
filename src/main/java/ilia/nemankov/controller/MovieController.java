package ilia.nemankov.controller;

import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.service.MovieService;
import ilia.nemankov.service.MovieServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;

@Path("/movies")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class MovieController extends HttpServlet {

    private final MovieService movieService;

    public MovieController() {
        this.movieService = new MovieServiceImpl();
    }

    @GET
    @Path("/{id}")
    public Response getMovie(@PathParam("id") Long id) {
        MovieDTO movie = movieService.findById(id);

        if (movie != null) {
            return Response.status(HttpServletResponse.SC_OK).entity(movie).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @GET
    @Path("")
    public Response getMovies(@QueryParam("count") Integer count, @QueryParam("page") Integer page,
                              @QueryParam("order") List<String> order, @QueryParam("filter") List<String> filter) throws ParseException {
        FilterConfiguration filterConfiguration = new FilterConfiguration();
        if (count != null && page != null) {
            filterConfiguration.setCount(count);
            filterConfiguration.setPage(page);
        }

        if (order != null && order.size() > 0) {
            filterConfiguration.setOrder(order);
        }

        if (filter != null && filter.size() > 0) {
            filterConfiguration.setFilter(filter);
        }

        List<MovieDTO> movies = movieService.findAll(filterConfiguration);

        if (movies.size() > 0) {
            return Response.status(HttpServletResponse.SC_OK).entity(movies).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @GET
    @Path("/golden_palm_count/avrg")
    public Response getGoldenPalmCount() {
        return Response.status(HttpServletResponse.SC_OK).entity(movieService.getAvrgGoldenPalmCount()).build();
    }

    @POST
    @Path("")
    public Response addMovie(MovieDTO movieDTO) {
        movieDTO.setId(null);
        MovieDTO savedValue = movieService.saveOrUpdate(movieDTO);
        return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
    }

    @PUT
    @Path("")
    public Response putMovie(MovieDTO movieDTO) {
        if (movieDTO.getId() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'id' must be specified").build());
        }

        MovieDTO savedValue = movieService.saveOrUpdate(movieDTO);
        return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        movieService.delete(id);
        return Response.status(HttpServletResponse.SC_OK).build();
    }
}
