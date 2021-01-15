package br.com.dca.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(of = {"name", "type"})
public class Pet extends BaseEntity {

    @Column(nullable = false, length = 50)
    @NotBlank(message = "pet.name.notBlank")
    @Size(min = 2, max = 50, message = "pet.name.size.min.max")
    private String name;

    @Column(nullable = false, length = 1)
    @Pattern(regexp = "^[M|F]{0,1}$", message = "pet.gender.invalid")
    private String gender;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "pet.type.notNull")
    private PetType type;

    @Column(length = 100)
    private String description;

    @Column
    @NotNull(message = "pet.dateLastVaccination.notNull")
    private LocalDate dateLastVaccination;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean castrated;

}
