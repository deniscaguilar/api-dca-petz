package br.com.dca.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Phone extends BaseEntity {

    @Column(nullable = false, length = 2)
    @NotBlank(message = "phone.areaCode.notBlank")
    private String areaCode;

    @Column(nullable = false, length = 9)
    @NotBlank(message = "phone.number.notBlank")
    private String number;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "phone.type.notNull")
    private PhoneType type;

}
