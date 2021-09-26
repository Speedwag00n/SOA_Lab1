package ilia.nemankov.service;

import ilia.nemankov.dto.CoordinatesDTO;

import java.util.List;

public interface CoordinatesService {

    List<CoordinatesDTO> findAll();

    CoordinatesDTO save(CoordinatesDTO dto);

    CoordinatesDTO findById(Long id);

    void delete(Long id);

}
