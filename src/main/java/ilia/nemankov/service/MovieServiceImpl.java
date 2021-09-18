package ilia.nemankov.service;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.entity.Movie;
import ilia.nemankov.mapper.MovieMapper;
import ilia.nemankov.repository.*;

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
    public List<MovieDTO> findAll(FilterConfiguration filterConfiguration) {
        List<Movie> movies =  movieRepository.findAll(filterConfiguration);

        List<MovieDTO> result = new ArrayList<>();

        for (Movie movie : movies) {
            result.add(movieMapper.entityToDto(movie));
        }

        return result;
    }

    @Override
    public MovieDTO save(MovieDTO dto) {
        Movie movie = movieMapper.dtoToEntity(dto);
        movie.setCoordinates(coordinatesRepository.findById(movie.getCoordinates().getId()));
        if (movie.getScreenwriter() != null) {
            movie.setScreenwriter(personRepository.findById(movie.getScreenwriter().getId()));
        }

        movieRepository.save(movie);
        return movieMapper.entityToDto(movie);
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
    public MovieDTO update(MovieDTO newValue) {
        Movie movie = movieMapper.dtoToEntity(newValue);
        movie.setCoordinates(coordinatesRepository.findById(movie.getCoordinates().getId()));
        if (movie.getScreenwriter() != null) {
            movie.setScreenwriter(personRepository.findById(movie.getScreenwriter().getId()));
        }

        MovieDTO updatedValue;
        updatedValue = movieMapper.entityToDto(movieRepository.update(movie));
        return updatedValue;
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
