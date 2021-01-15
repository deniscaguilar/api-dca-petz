package br.com.dca.usecases;

import br.com.dca.domains.Pet;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.PetGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FindPet {

    private PetGateway petGateway;

    public Pet byId(final Long id) {
        log.info("Finding pet by id: {}", id);

        return petGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pet not found by id: %s", id)));
    }

    public Collection<Pet> all() {
        log.info("Finding all pets");

        return petGateway.findAll();
    }

}
