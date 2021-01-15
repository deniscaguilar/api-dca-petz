package br.com.dca.gateways.http;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.dca.domains.Customer;
import br.com.dca.exceptions.CustomerAlreadyExistsException;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.http.contracts.CustomerContract;
import br.com.dca.gateways.http.converters.CustomerConverter;
import br.com.dca.usecases.CreateCustomer;
import br.com.dca.usecases.DeleteCustomer;
import br.com.dca.usecases.FindCustomer;
import br.com.dca.usecases.UpdateCustomer;
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
@RequestMapping("/customers")
@Api(value = "Rest API para executar as ações CRUD da entidade Customer")
public class CustomerController {

    private CreateCustomer createCustomer;

    private UpdateCustomer updateCustomer;

    private DeleteCustomer deleteCustomer;

    private FindCustomer findCustomer;

    @ApiOperation(value = "Retorna todos os clientes cadastrados")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Clientes encontrados", response = CustomerContract.class))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerContract> findAll() {
        final Collection<Customer> customers = findCustomer.all();

        return customers.stream()
                .map(customer -> CustomerConverter.convertFromDomainToContract(customer))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Retorna um cliente a partir de seu id de cadastro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente encontrado", response = CustomerContract.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o id informado", response = ResourceNotFoundException.class)}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerContract findById(@PathVariable(name = "idCustomer") final Long idCustomer) {
        final Customer customer = findCustomer.byId(idCustomer);
        return CustomerConverter.convertFromDomainToContract(customer);
    }

    @ApiOperation(value = "Exclui um cliente cadastrado a partir de seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cliente excluído"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o id informado", response = ResourceNotFoundException.class)}
    )
    @DeleteMapping(value = "/{idCustomer}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "idCustomer") final Long idCustomer) {
        deleteCustomer.execute(idCustomer);
    }

    @ApiOperation(value = "Atualiza os dados de um cliente cadastrado a partir de seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente atualizado", response = CustomerContract.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o id informado", response = ResourceNotFoundException.class)}
    )
    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerContract update(@PathVariable(name = "idCustomer") final Long idCustomer, @RequestBody final CustomerContract customerContract) {
        final Customer customer = updateCustomer.execute(CustomerConverter.convertFromContractToDomain(idCustomer, customerContract));
        return CustomerConverter.convertFromDomainToContract(customer);
    }

    @ApiOperation(value = "Cria um novo Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Novo cliente criado", response = CustomerContract.class),
            @ApiResponse(code = 422, message = "Cliente já existe no cadastro", response = CustomerAlreadyExistsException.class)
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerContract create(@RequestBody final CustomerContract customerContract) {
        final Customer customer = createCustomer.execute(CustomerConverter.convertFromContractToDomain(customerContract));
        return CustomerConverter.convertFromDomainToContract(customer);
    }

}
