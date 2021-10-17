package ilia.nemankov.service;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.dto.MovieDTO;

import java.text.ParseException;
import java.util.List;

public interface MovieService {

    List<MovieDTO> findAll(FilterConfiguration filterConfiguration) throws ParseException;

    MovieDTO saveOrUpdate(MovieDTO dto);

    MovieDTO findById(Long id);

    void delete(Long id);

    double getAvrgGoldenPalmCount();

}
