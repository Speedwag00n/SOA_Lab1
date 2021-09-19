package ilia.nemankov.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "MOVIE")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Movie {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "COORDINATES")
    private Coordinates coordinates;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "OSCARS_COUNT")
    private Integer oscarsCount;

    @Column(name = "GOLDEN_PALM_COUNT")
    private Long goldenPalmCount;

    @Column(name = "TOTAL_BOX_OFFICE")
    private Double totalBoxOffice;

    @Column(name = "MPAA_RATING")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private MPAARating mpaaRating;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "SCREEN_WRITER")
    private Person screenWriter;
}
