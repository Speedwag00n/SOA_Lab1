package ilia.nemankov.service;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.entity.Coordinates;
import ilia.nemankov.entity.Movie;
import ilia.nemankov.filters.MovieFilter;
import ilia.nemankov.mapper.MovieMapper;
import ilia.nemankov.repository.*;
import ilia.nemankov.utils.Error;
import ilia.nemankov.utils.MissingEntityException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {

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
        List<Movie> movies =  movieRepository.findAll(filterConfiguration);

        List<MovieDTO> result = new ArrayList<>();

        for (Movie movie : movies) {
            result.add(movieMapper.entityToDto(movie));
        }

        return result;
    }

    @Override
    public MovieDTO saveOrUpdate(MovieDTO dto) {
        Movie movie = movieMapper.dtoToEntity(dto);
        movie.setCoordinates(coordinatesRepository.findById(movie.getCoordinates().getId()));
        if (movie.getCoordinates() == null) {
            throw new MissingEntityException(new Error(MovieFilter.MISSING_COORDINATES_ENTITY, "Could not found specified coordinates"));
        }
        if (movie.getScreenWriter() != null) {
            movie.setScreenWriter(personRepository.findById(movie.getScreenWriter().getId()));
            if (movie.getScreenWriter() == null) {
                throw new MissingEntityException(new Error(MovieFilter.MISSING_SCREEN_WRITER_ENTITY, "Could not found specified screen writer"));
            }
        }

        if (movie.getId() == null || movieRepository.findById(movie.getId()) == null) {
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
}
