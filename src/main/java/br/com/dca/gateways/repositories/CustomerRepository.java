package br.com.dca.gateways.repositories;

import br.com.dca.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByEmail(String email);
}
