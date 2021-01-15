package br.com.dca.gateways.http;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.dca.domains.Pet;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.http.contracts.PetContract;
import br.com.dca.gateways.http.converters.PetConverter;
import br.com.dca.usecases.CreatePet;
import br.com.dca.usecases.DeletePet;
import br.com.dca.usecases.FindPet;
import br.com.dca.usecases.UpdatePet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/pets")
@Api(value = "Rest API para executar as ações CRUD da entidade Pet")
public class PetController {

    private CreatePet createPet;

    private UpdatePet updatePet;

    private DeletePet deletePet;

    private FindPet findPet;

    @ApiOperation(value = "Retorna todos os pets cadastrados")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Pets encontrados", response = PetContract.class))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PetContract> findAll() {
        final Collection<Pet> pets = findPet.all();

        return pets.stream()
                .map(pet -> PetConverter.convertFromDomainToContract(pet))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Retorna um pet a partir de seu id de cadastro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pet encontrado", response = PetContract.class),
            @ApiResponse(code = 404, message = "Pet não encontrado para o id informado", response = ResourceNotFoundException.class)}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idPet}")
    @ResponseStatus(HttpStatus.OK)
    public PetContract findById(@PathVariable(name = "idPet") final Long idPet) {
        final Pet pet = findPet.byId(idPet);
        return PetConverter.convertFromDomainToContract(pet);
    }

    @ApiOperation(value = "Exclui um pet cadastrado a partir de seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Pet excluído"),
            @ApiResponse(code = 404, message = "Pet não encontrado para o id informado", response = ResourceNotFoundException.class)}
    )
    @DeleteMapping(value = "/{idPet}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "idPet") final Long idPet) {
        deletePet.execute(idPet);
    }

    @ApiOperation(value = "Atualiza os dados de um pet cadastrado a partir de seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pet atualizado", response = PetContract.class),
            @ApiResponse(code = 404, message = "Pet não encontrado para o id informado", response = ResourceNotFoundException.class)}
    )
    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "/{idPet}")
    @ResponseStatus(HttpStatus.OK)
    public PetContract update(@PathVariable(name = "idPet") final Long idPet, @RequestBody final PetContract petContract) {
        final Pet pet = updatePet.execute(PetConverter.convertFromContractToDomain(idPet, petContract));
        return PetConverter.convertFromDomainToContract(pet);
    }

    @ApiOperation(value = "Cria um novo Pet")
    @ApiResponses(value = @ApiResponse(code = 201, message = "Novo pet criado", response = PetContract.class))
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PetContract create(@RequestBody final PetContract petContract) {
        final Pet pet = createPet.execute(PetConverter.convertFromContractToDomain(petContract));
        return PetConverter.convertFromDomainToContract(pet);
    }
}
