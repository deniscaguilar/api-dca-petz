package br.com.dca.gateways.impl;

import br.com.dca.domains.Pet;
import br.com.dca.gateways.PetGateway;
import br.com.dca.gateways.repositories.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PetGatewayImpl implements PetGateway {

    private PetRepository petRepository;

    @Override
    public Pet save(final Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void delete(final Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    public Optional<Pet> findById(final Long id) {
        return petRepository.findById(id);
    }

    @Override
    public Collection<Pet> findAll() {
        return petRepository.findAll();
    }
}
