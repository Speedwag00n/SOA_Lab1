package ilia.nemankov.service;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.entity.MPAARating;
import ilia.nemankov.entity.Movie;
import ilia.nemankov.mapper.MovieMapper;
import ilia.nemankov.repository.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieServiceImpl implements MovieService {

    private static final List<String> EXPECTED_FIELDS = Collections.unmodifiableList(Arrays.asList("id", "name", "coordinates", "creationDate", "oscarsCount", "goldenPalmCount", "totalBoxOffice", "mpaaRating", "screenWriter", "genre"));
    private static final List<String> EXPECTED_ACTIONS = Collections.unmodifiableList(Arrays.asList("<", ">", "==", "<=", ">=", "contains"));

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;

    public MovieServiceImpl() {
        this.movieRepository = new MovieRepositoryImpl();
        this.movieMapper = new MovieMapper();
        this.coordinatesRepository = new CoordinatesRepositoryImpl();
        this.personRepository = new PersonRepositoryImpl();
    }

    @Override
    public List<MovieDTO> findAll(FilterConfiguration filterConfiguration) throws ParseException {
        validateOptions(filterConfiguration);
        List<Movie> movies =  movieRepository.findAll(filterConfiguration);

        List<MovieDTO> result = new ArrayList<>();

        for (Movie movie : movies) {
            result.add(movieMapper.entityToDto(movie));
        }

        return result;
    }

    @Override
    public MovieDTO saveOrUpdate(MovieDTO dto) {
        validateDTO(dto);
        Movie movie = movieMapper.dtoToEntity(dto);
        movie.setCoordinates(coordinatesRepository.findById(movie.getCoordinates().getId()));
        if (movie.getCoordinates() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Could not found specified coordinates").build());
        }
        if (movie.getScreenWriter() != null) {
            movie.setScreenWriter(personRepository.findById(movie.getScreenWriter().getId()));
            if (movie.getScreenWriter() == null) {
                throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("\"Could not found specified screen writer").build());
            }
        }

        if (movie.getId() == null || movieRepository.findById(movie.getId()) == null) {
            movie.setCreationDate(new Date());
            movieRepository.save(movie);
            return movieMapper.entityToDto(movie);
        } else {
            Movie updatedValue = movieRepository.update(movie);
            return movieMapper.entityToDto(updatedValue);
        }
    }

    @Override
    public MovieDTO findById(Long id) {
        Movie movie = movieRepository.findById(id);

        if (movie != null) {
            return movieMapper.entityToDto(movie);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        movieRepository.delete(id);
    }

    @Override
    public double getAvrgGoldenPalmCount() {
        List<Movie> movies =  movieRepository.findAll();

        double result = 0;

        for (Movie movie : movies) {
            result += movie.getGoldenPalmCount();
        }

        if (movies.size() == 0) {
            return 0;
        } else {
            return result / movies.size();
        }
    }

    private void validateDTO(MovieDTO dto) {
        if (dto == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Request body must be specified").build());
        }

        if (dto.getName() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'name' must be specified").build());
        }

        if (dto.getCoordinates() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'coordinates' must be specified").build());
        }

        if (dto.getOscarsCount() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'oscarsCount' must be specified").build());
        }

        if (dto.getGoldenPalmCount() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'goldenPalmCount' must be specified").build());
        }

        if (dto.getGenre() == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'genre' must be specified").build());
        }

        if (!(dto.getName().length() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Length of field 'name' must be bigger than 0").build());
        }

        if (!(dto.getOscarsCount() >= 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'oscarsCount' must be bigger than or equal to 0").build());
        }

        if (!(dto.getGoldenPalmCount() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'goldenPalmCount' must be bigger than 0").build());
        }

        if (dto.getTotalBoxOffice() != null && !(dto.getTotalBoxOffice() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'totalBoxOffice' must be bigger than 0").build());
        }

        if (!(dto.getGenre().length() < 32) || !(dto.getGenre().length() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Length of field 'genre' must be bigger than 0 and less than 32").build());
        }
    }

    private void validateOptions(FilterConfiguration filterConfiguration) {
        if (filterConfiguration.getCount() != null && !(filterConfiguration.getCount() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Parameter 'count' must be bigger than 0").build());
        }

        if (filterConfiguration.getPage() != null && !(filterConfiguration.getPage() > 0)) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Parameter 'page' must be bigger than 0").build());
        }

        if (filterConfiguration.getOrder() != null) {
            for (String order : filterConfiguration.getOrder()) {
                String[] parts = order.split(",");
                if (parts.length == 2) {
                    if (!EXPECTED_FIELDS.contains(parts[0])) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Unexpected field '" + parts[0] + "' specified to order by '" + order + "'. Check documentation for details.").build());
                    }
                    if (!parts[1].equals("d") && !parts[1].equals("a")) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Order direction must be 'a' (asc) or 'd' (desc).").build());
                    }
                } else {
                    throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Order parameter has invalid format '" + order + "'. Check documentation for details.").build());
                }
            }
        }

        if (filterConfiguration.getFilter() != null) {
            for (String filter : filterConfiguration.getFilter()) {
                String[] parts = filter.split(",");

                if (!EXPECTED_FIELDS.contains(parts[0])) {
                    throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Unexpected field '" + parts[0] + "' specified in filter '" + filter + "'. Check documentation for details.").build());
                }

                if (parts.length != 3) {
                    throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Filter parameter has invalid format '" + filter + "'. Check documentation for details.").build());
                }

                if (parts[0].equals("id")) {
                    try {
                        Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'id' in filter must be integer").build());
                    }
                }

                if (parts[0].equals("coordinates")) {
                    try {
                        Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'coordinates' in filter must be integer").build());
                    }
                }

                if (parts[0].equals("creationDate")) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        formatter.parse(parts[2]);
                    } catch (ParseException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'creationDate' in filter must have format yyyy-MM-dd").build());
                    }
                }

                if (parts[0].equals("oscarsCount")) {
                    try {
                        Integer.parseInt(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'oscarsCount' in filter must be integer").build());
                    }
                }

                if (parts[0].equals("goldenPalmCount")) {
                    try {
                        Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'goldenPalmCount' in filter must be integer").build());
                    }
                }

                if (parts[0].equals("totalBoxOffice")) {
                    try {
                        Double.parseDouble(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'totalBoxOffice' in filter must be integer").build());
                    }
                }

                if (parts[0].equals("mpaaRating")) {
                    try {
                        MPAARating.valueOf(parts[2]);
                    } catch (IllegalArgumentException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'mpaaRating' in filter must be one of expected value. Check documentation for details").build());
                    }
                }

                if (parts[0].equals("screenWriter")) {
                    try {
                        Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Field 'screenWriter' in filter must be integer").build());
                    }
                }

                if (!EXPECTED_ACTIONS.contains(parts[1])) {
                    throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Unexpected action '" + parts[0] + "' specified in filter '" + filter + "'. Check documentation for details.").build());
                }
            }
        }
    }
}
