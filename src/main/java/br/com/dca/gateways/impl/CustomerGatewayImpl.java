package br.com.dca.gateways.impl;

import br.com.dca.domains.Customer;
import br.com.dca.gateways.CustomerGateway;
import br.com.dca.gateways.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {

    private CustomerRepository customerRepository;

    @Override
    public Customer save(final Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(final Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public Optional<Customer> findById(final Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Collection<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {
        return Optional.ofNullable(customerRepository.findOneByEmail(email));
    }
}
