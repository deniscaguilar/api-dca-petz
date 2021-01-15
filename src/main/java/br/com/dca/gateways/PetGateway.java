package br.com.dca.gateways;

import br.com.dca.domains.Pet;

import java.util.Collection;
import java.util.Optional;

public interface PetGateway {

    Pet save(Pet pet);

    void delete(Pet pet);

    Optional<Pet> findById(Long id);

    Collection<Pet> findAll();
}
