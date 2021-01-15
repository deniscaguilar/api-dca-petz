package br.com.dca.usecases;

import br.com.dca.domains.Pet;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.PetGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class DeletePet {

    private PetGateway petGateway;

    public void execute(final Long id) {
        log.info("Deleting pet by id: {}", id);

        final Pet pet = findPet(id);
        petGateway.delete(pet);
    }

    private Pet findPet(final Long id) {
        return petGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pet not found by id: %s", id)));
    }

}