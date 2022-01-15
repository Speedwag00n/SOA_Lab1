package ilia.nemankov.service;

import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.entity.Coordinates;
import ilia.nemankov.mapper.CoordinatesMapper;
import ilia.nemankov.repository.CoordinatesRepository;
import ilia.nemankov.repository.CoordinatesRepositoryImpl;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Stateless
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
    public CoordinatesDTO save(CoordinatesDTO dto) throws BadResponseException {
        validate(dto);
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
    public void delete(Long id) throws BadResponseException {
        coordinatesRepository.delete(id);
    }

    private void validate(CoordinatesDTO dto) throws BadResponseException {
        if (dto == null) {
            throw new BadResponseException("Request body must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (dto.getX() == null) {
            throw new BadResponseException("Field 'x' must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (dto.getY() == null) {
            throw new BadResponseException("Field 'y' must be specified", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!(dto.getX() > -593)) {
            throw new BadResponseException("Field 'x' must be bigger than -593", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!(dto.getY() > -148)) {
            throw new BadResponseException("Field 'y' must be bigger than -148", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
