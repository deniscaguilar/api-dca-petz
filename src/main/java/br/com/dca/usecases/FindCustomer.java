package br.com.dca.usecases;

import br.com.dca.domains.Customer;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FindCustomer {

    private CustomerGateway customerGateway;

    public Customer byId(final Long id) {
        log.info("Finding customer by id: {}", id);

        return customerGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer not found by id: %s", id)));
    }

    public Collection<Customer> all() {
        log.info("Finding all customers");

        return customerGateway.findAll();
    }

}