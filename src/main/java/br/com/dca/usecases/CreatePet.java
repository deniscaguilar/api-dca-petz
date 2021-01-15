package br.com.dca.usecases;

import br.com.dca.domains.Pet;
import br.com.dca.gateways.PetGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Service
@Validated
@AllArgsConstructor
@Transactional
public class CreatePet {

    private PetGateway petGateway;

    public Pet execute(@Valid final Pet pet) {
        log.info("Creating pet: {}", pet);

        return petGateway.save(pet);
    }

}
