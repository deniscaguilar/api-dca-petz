package br.com.dca.usecases;

import br.com.dca.domains.Customer;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.CustomerGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class DeleteCustomer {

    private CustomerGateway customerGateway;

    public void execute(final Long id) {
        log.info("Deleting customer by id: {}", id);

        final Customer customer = findCustomer(id);
        customerGateway.delete(customer);
    }

    private Customer findCustomer(final Long id) {
        return customerGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer not found by id: %s", id)));
    }

}