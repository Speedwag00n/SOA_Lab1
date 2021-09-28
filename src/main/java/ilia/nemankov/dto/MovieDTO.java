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
    private CoordinatesDTO coordinates;
    private Date creationDate;
    private Integer oscarsCount;
    private long goldenPalmCount;
    private Double totalBoxOffice;
    private MPAARating mpaaRating;
    private PersonDTO screenWriter;
}
