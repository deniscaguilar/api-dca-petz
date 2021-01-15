package br.com.dca.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString(of = {"name", "email"})
public class Customer extends BaseEntity {

    @Column(nullable = false, length = 80)
    @NotBlank(message = "customer.name.notBlank")
    @Size(min = 2, max = 80, message = "customer.name.size.min.max")
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "customer.email.invalid")
    @NotBlank(message = "customer.email.notBlank")
    @Size(min = 8, max = 100, message = "customer.email.size.min.max")
    private String email;

    @Column(nullable = false, length = 1)
    @Pattern(regexp = "^[M|F]{0,1}$", message = "customer.gender.invalid")
    private String gender;

    @Column(nullable = false)
    @NotNull(message = "customer.dateOfBirth.notNull")
    private LocalDate dateOfBirth;

    @NotNull(message = "customer.cpf.notNull")
    @CPF(message = "customer.cpf.invalid")
    @Column(nullable = false, length = 12)
    private String cpf;

    @Valid
    @NotNull(message = "customer.phones.notNull")
    @Size(min = 1, message = "customer.phones.size.min")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Phone> phones;

    @Valid
    @NotNull(message = "customer.addresses.notNull")
    @Size(min = 1, message = "customer.addresses.size.min")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Address> addresses;

}
