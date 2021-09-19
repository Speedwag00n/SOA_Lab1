package ilia.nemankov.service;

import ilia.nemankov.controller.FilterConfiguration;
import ilia.nemankov.dto.MovieDTO;

import java.text.ParseException;
import java.util.List;

public interface MovieService {

    List<MovieDTO> findAll(FilterConfiguration filterConfiguration) throws ParseException;

    MovieDTO save(MovieDTO dto);

    MovieDTO findById(Long id);

    MovieDTO update(MovieDTO newValue);

    void delete(Long id);

    double getAvrgGoldenPalmCount();

}
