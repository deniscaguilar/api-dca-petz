package br.com.dca.gateways.http.converters;

import br.com.dca.domains.Pet;
import br.com.dca.domains.PetType;
import br.com.dca.gateways.http.contracts.PetContract;
import br.com.dca.gateways.http.contracts.PetTypeContract;
import org.springframework.beans.BeanUtils;

public class PetConverter {

    public static Pet convertFromContractToDomain(final Long idPet, final PetContract petContract) {
        final Pet pet = new Pet();
        BeanUtils.copyProperties(petContract, pet, "type");
        pet.setId(idPet);
        pet.setType(PetType.valueOf(petContract.getType().name()));
        return pet;
    }

    public static Pet convertFromContractToDomain(final PetContract petContract) {
        final Pet pet = new Pet();
        BeanUtils.copyProperties(petContract, pet, "type");
        pet.setType(PetType.valueOf(petContract.getType().name()));
        return pet;
    }

    public static PetContract convertFromDomainToContract(final Pet pet) {
        final PetContract petContract = new PetContract();
        BeanUtils.copyProperties(pet, petContract, "type");
        petContract.setType(PetTypeContract.valueOf(pet.getType().name()));
        return petContract;
    }
}
