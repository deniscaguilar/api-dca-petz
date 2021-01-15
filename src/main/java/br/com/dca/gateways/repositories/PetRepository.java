package br.com.dca.gateways.repositories;

import br.com.dca.domains.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
