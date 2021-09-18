package ilia.nemankov.mapper;

import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Movie;
import ilia.nemankov.entity.Person;

public class MovieMapper {
    private final CoordinatesMapper coordinatesMapper = new CoordinatesMapper();
    private final PersonMapper personMapper = new PersonMapper();

    public Movie dtoToEntity(MovieDTO dto) {
        Movie movie = new Movie();

        movie.setId(dto.getId());
        movie.setName(dto.getName());
        movie.setCoordinates(coordinatesMapper.dtoToEntity(dto.getCoordinatesDTO()));
        movie.setCreationDate(dto.getCreationDate());
        movie.setOscarsCount(dto.getOscarsCount());
        movie.setGoldenPalmCount(dto.getGoldenPalmCount());
        movie.setTotalBoxOffice(dto.getTotalBoxOffice());
        movie.setMpaaRating(dto.getMpaaRating());
        movie.setScreenwriter(personMapper.dtoToEntity(dto.getScreenWriter()));

        return movie;
    }

    public MovieDTO entityToDto(Movie entity) {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(entity.getId());
        movieDTO.setName(entity.getName());
        movieDTO.setCoordinatesDTO(coordinatesMapper.entityToDto(entity.getCoordinates()));
        movieDTO.setCreationDate(entity.getCreationDate());
        movieDTO.setOscarsCount(entity.getOscarsCount());
        movieDTO.setGoldenPalmCount(entity.getGoldenPalmCount());
        movieDTO.setTotalBoxOffice(entity.getTotalBoxOffice());
        movieDTO.setMpaaRating(entity.getMpaaRating());
        movieDTO.setScreenWriter(personMapper.entityToDto(entity.getScreenwriter()));

        return movieDTO;
    }

}
