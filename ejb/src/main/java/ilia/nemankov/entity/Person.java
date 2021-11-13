package ilia.nemankov.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.validation.ValidationException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "PERSON")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Person {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HEIGHT")
    private Long height;

    @Column(name = "PASSPORT_ID")
    private String passportId;

    @Column(name = "NATIONALITY")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private Country nationality;

    public Person(String name, Long height, String passportId, Country nationality) {
        this.name = name;
        this.height = height;
        this.passportId = passportId;
        this.nationality = nationality;
    }
}
