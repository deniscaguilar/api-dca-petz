package br.com.dca.usecases;

import br.com.dca.domains.Pet;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.PetGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
@Validated
@AllArgsConstructor
@Transactional
public class UpdatePet {

    private PetGateway petGateway;

    public Pet execute(@Valid final Pet pet) {
        log.info("Updating pet: {}", pet);

        isPetValid(pet.getId());
        return petGateway.save(pet);
    }

    private void isPetValid(final Long id) {
        final Optional<Pet> optPet = petGateway.findById(id);

        if (!optPet.isPresent()) {
            throw new ResourceNotFoundException(String.format("Pet not found by id: %s", id));
        }
    }

}
