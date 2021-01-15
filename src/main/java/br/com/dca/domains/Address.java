package br.com.dca.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Address extends BaseEntity {

    @Column(nullable = false, length = 30)
    @NotBlank(message = "address.name.notBlank")
    private String name;

    @Column(nullable = false, length = 8)
    @NotBlank(message = "address.zipCode.notBlank")
    @Size(max = 8, message = "address.zipCode.size.min")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "address.zipCode.invalid")
    private String zipCode;

    @Column(nullable = false, length = 90)
    @NotBlank(message = "address.streetName.notBlank")
    @Size(max = 90, message = "address.streetName.size.max")
    private String streetName;

    @Column(nullable = false, length = 90)
    @NotBlank(message = "address.streetNumber.notBlank")
    @Size(max = 7, message = "address.streetNumber.size.max")
    private String streetNumber;

    @Column(length = 30)
    @Size(max = 30, message = "address.additionalInfo.size.max")
    private String complement;

    @Column(nullable = false, length = 60)
    @NotBlank(message = "address.neighborhood.notBlank")
    @Size(max = 60, message = "address.neighborhood.size.max")
    private String neighborhood;

    @Column(nullable = false, length = 90)
    @NotBlank(message = "address.city.notBlank")
    @Size(max = 90, message = "address.city.size.max")
    private String city;

    @Column(nullable = false, length = 2)
    @NotBlank(message = "address.state.notBlank")
    @Size(max = 2, message = "address.state.size.max")
    private String state;

    @Column(length = 140)
    @Size(max = 140, message = "address.reference.size.max")
    private String reference;

}
