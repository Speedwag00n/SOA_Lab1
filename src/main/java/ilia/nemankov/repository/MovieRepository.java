package ilia.nemankov.repository;

import ilia.nemankov.entity.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();

    void save(Movie coordinates);

    Movie findById(Long id);

    Movie update(Movie newValue);

    void delete(Long id);
}
