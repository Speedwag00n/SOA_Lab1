package ilia.nemankov.service;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.dto.MovieDTO;

import javax.ejb.Remote;
import java.text.ParseException;
import java.util.List;

@Remote
public interface MovieService {

    List<MovieDTO> findAll(FilterConfiguration filterConfiguration) throws Exception;

    MovieDTO saveOrUpdate(MovieDTO dto) throws BadResponseException;

    MovieDTO findById(Long id);

    void delete(Long id) throws BadResponseException;

    double getAvrgGoldenPalmCount();

}
