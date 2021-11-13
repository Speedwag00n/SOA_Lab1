package ilia.nemankov.repository;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.entity.Movie;
import ilia.nemankov.service.BadResponseException;

import java.text.ParseException;
import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();

    List<Movie> findAll(FilterConfiguration filterConfiguration) throws ParseException;

    void save(Movie coordinates);

    Movie findById(Long id);

    Movie update(Movie newValue);

    void delete(Long id) throws BadResponseException;
}
