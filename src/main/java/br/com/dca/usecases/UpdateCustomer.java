package br.com.dca.usecases;

import br.com.dca.domains.Customer;
import br.com.dca.exceptions.ResourceNotFoundException;
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
public class UpdateCustomer {

    private CustomerGateway customerGateway;

    public Customer execute(@Valid final Customer customer) {
        log.info("Updating customer: {}", customer);

        isCustomerValid(customer.getId());
        return customerGateway.save(customer);
    }

    private void isCustomerValid(final Long id) {
        final Optional<Customer> optCustomer = customerGateway.findById(id);

        if (!optCustomer.isPresent()) {
            throw new ResourceNotFoundException(String.format("Customer not found by id: %s", id));
        }
    }

}