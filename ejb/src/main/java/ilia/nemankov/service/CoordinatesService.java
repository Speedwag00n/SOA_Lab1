package ilia.nemankov.service;

import ilia.nemankov.dto.CoordinatesDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CoordinatesService {

    List<CoordinatesDTO> findAll();

    CoordinatesDTO save(CoordinatesDTO dto) throws BadResponseException;

    CoordinatesDTO findById(Long id);

    void delete(Long id) throws BadResponseException;

}
