package ilia.nemankov.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "COORDINATES")
public class Coordinates {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "X")
    private Double x;

    @Column(name = "Y")
    private Long y;

    public Coordinates(Double x, Long y) {
        this.x = x;
        this.y = y;
    }
}
