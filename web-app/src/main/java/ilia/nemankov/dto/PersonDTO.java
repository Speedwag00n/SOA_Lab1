package ilia.nemankov.dto;

import ilia.nemankov.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {

    public PersonDTO(Long id) {
        this.id = id;
    }

    private Long id;
    private String name;
    private Long height;
    private String passportId;
    private Country nationality;
}
