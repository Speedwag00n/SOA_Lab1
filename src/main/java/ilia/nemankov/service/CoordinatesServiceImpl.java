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

    @Override
    public CoordinatesDTO save(CoordinatesDTO dto) {
        Coordinates coordinates = coordinatesMapper.dtoToEntity(dto);

        coordinatesRepository.save(coordinates);
        return coordinatesMapper.entityToDto(coordinates);
    }

    @Override
    public CoordinatesDTO findById(Long id) {
        Coordinates coordinates = coordinatesRepository.findById(id);

        if (coordinates != null) {
            return coordinatesMapper.entityToDto(coordinates);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        coordinatesRepository.delete(id);
    }
}
