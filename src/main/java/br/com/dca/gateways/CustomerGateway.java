package br.com.dca.gateways;

import br.com.dca.domains.Customer;

import java.util.Collection;
import java.util.Optional;

public interface CustomerGateway {

    Customer save(Customer customer);

    void delete(Customer customer);

    Optional<Customer> findById(Long id);

    Collection<Customer> findAll();

    Optional<Customer> findByEmail(String email);
}
