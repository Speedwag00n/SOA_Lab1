package ilia.nemankov.mapper;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.entity.Coordinates;

public class CoordinatesMapper {

    public Coordinates dtoToEntity(CoordinatesDTO dto) {
        Coordinates coordinates = new Coordinates();

        coordinates.setId(dto.getId());
        coordinates.setX(dto.getX());
        coordinates.setY(dto.getY());

        return coordinates;
    }

    public CoordinatesDTO entityToDto(Coordinates entity) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();

        coordinatesDTO.setId(entity.getId());
        coordinatesDTO.setX(entity.getX());
        coordinatesDTO.setY(entity.getY());

        return coordinatesDTO;
    }

}
