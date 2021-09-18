package ilia.nemankov.repository;

import ilia.nemankov.entity.Coordinates;

import java.util.List;

public interface CoordinatesRepository {
    List<Coordinates> findAll();

    void save(Coordinates coordinates);

    Coordinates findById(Long id);

    Coordinates update(Coordinates newValue);

    void delete(Long id);
}
