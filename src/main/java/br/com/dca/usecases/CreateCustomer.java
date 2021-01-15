package br.com.dca.usecases;

import br.com.dca.domains.Customer;
import br.com.dca.exceptions.CustomerAlreadyExistsException;
import br.com.dca.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
@Validated
@AllArgsConstructor
@Transactional
public class CreateCustomer {

    private CustomerGateway customerGateway;

    public Customer execute(@Valid final Customer customer) {
        log.info("Creating customer: {}", customer);

        isAlreadyExistsCustomer(customer.getEmail());
        return customerGateway.save(customer);
    }

    private void isAlreadyExistsCustomer(final String email) {
        final Optional<Customer> optCustomer = customerGateway.findByEmail(email);

        if (optCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(email);
        }
    }

}
