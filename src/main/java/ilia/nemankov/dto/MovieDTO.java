package ilia.nemankov.dto;

import ilia.nemankov.entity.MPAARating;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MovieDTO {
    private Long id;
    private String name;
    private CoordinatesDTO coordinatesDTO;
    private Date creationDate;
    private Integer oscarsCount;
    private Long goldenPalmCount;
    private Double totalBoxOffice;
    private MPAARating mpaaRating;
    private PersonDTO screenWriter;
}
