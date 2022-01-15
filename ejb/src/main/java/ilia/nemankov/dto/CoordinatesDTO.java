package ilia.nemankov.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CoordinatesDTO implements Serializable {

    public CoordinatesDTO(Long id) {
        this.id = id;
    }

    private Long id;

    private Double x;

    private Long y;
}
