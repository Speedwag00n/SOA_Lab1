package ilia.nemankov.controller;

import ilia.nemankov.dto.Filter;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.service.BadResponseException;
import ilia.nemankov.service.MovieService;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Stateless
@WebService(
        targetNamespace = "http://localhost:8090/ws/movie"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class MovieController {

    private final MovieService movieService;

    public MovieController() throws NamingException {
        Context context = new ContextProviderImpl().getContext();
        Object ref = context.lookup("pool/MovieServiceImpl!ilia.nemankov.service.MovieService");
        this.movieService = (MovieService) PortableRemoteObject.narrow(ref, MovieService.class);
    }

    @WebMethod
    public MovieDTO getMovie(@WebParam(name = "id") Long id) throws BadResponseException {
        MovieDTO result = movieService.findById(id);

        if (result != null) {
            return result;
        } else {
            throw new BadResponseException("Not found", 404);
        }
    }

    @WebMethod
    public List<MovieDTO> getMovies(@WebParam(name = "filter") Filter filter) throws Exception {
        FilterConfiguration filterConfiguration = new FilterConfiguration();

        if (filter.getCount() != null && filter.getPage() != null) {
            filterConfiguration.setCount(filter.getCount());
            filterConfiguration.setPage(filter.getPage());
        }

        if (filter.getOrder() != null && filter.getOrder().length > 0) {
            filterConfiguration.setOrder(Arrays.asList(filter.getOrder()));
        }

        if (filter.getFilter() != null && filter.getFilter().length > 0) {
            filterConfiguration.setFilter(Arrays.asList(filter.getFilter()));
        }

        return movieService.findAll(filterConfiguration);
    }

    @WebMethod
    public Double getGoldenPalmCount() {
        return movieService.getAvrgGoldenPalmCount();
    }

    @WebMethod
    public MovieDTO addMovie(@WebParam(name = "movie") MovieDTO movieDTO) throws BadResponseException {
        movieDTO.setId(null);
        return movieService.saveOrUpdate(movieDTO);
    }

    @WebMethod
    public MovieDTO putMovie(@WebParam(name = "newMovie") MovieDTO movieDTO) throws BadResponseException {
        if (movieDTO.getId() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'id' must be specified").build());
        }

        return movieService.saveOrUpdate(movieDTO);
    }

    @WebMethod
    public boolean deleteMovie(@WebParam(name = "deleteId") Long id) throws BadResponseException {
        movieService.delete(id);
        return true;
    }
}
