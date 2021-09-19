package ilia.nemankov.service;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.entity.Coordinates;
import ilia.nemankov.mapper.CoordinatesMapper;
import ilia.nemankov.repository.CoordinatesRepository;
import ilia.nemankov.repository.CoordinatesRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesServiceImpl implements CoordinatesService {

    private final CoordinatesRepository coordinatesRepository;
    private final CoordinatesMapper coordinatesMapper;

    public CoordinatesServiceImpl() {
        this.coordinatesRepository = new CoordinatesRepositoryImpl();
        this.coordinatesMapper = new CoordinatesMapper();
    }

    @Override
    public List<CoordinatesDTO> findAll() {
        List<Coordinates> coordinates =  coordinatesRepository.findAll();

        List<CoordinatesDTO> result = new ArrayList<>();

        for (Coordinates element : coordinates) {
            result.add(coordinatesMapper.entityToDto(element));
        }

        return result;
    }
}
