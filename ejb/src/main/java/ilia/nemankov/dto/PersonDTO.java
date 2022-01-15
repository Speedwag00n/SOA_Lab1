package ilia.nemankov.dto;

import ilia.nemankov.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PersonDTO implements Serializable {

    public PersonDTO(Long id) {
        this.id = id;
    }

    private Long id;
    private String name;
    private Long height;
    private String passportId;
    private Country nationality;
}
