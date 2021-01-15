package br.com.dca.gateways.http;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.dca.domains.Customer;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.http.contracts.CustomerContract;
import br.com.dca.gateways.http.contracts.PhoneTypeContract;
import br.com.dca.usecases.CreateCustomer;
import br.com.dca.usecases.DeleteCustomer;
import br.com.dca.usecases.FindCustomer;
import br.com.dca.usecases.UpdateCustomer;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private FindCustomer findCustomer;

    @MockBean
    private DeleteCustomer deleteCustomer;

    @MockBean
    private UpdateCustomer updateCustomer;

    @MockBean
    private CreateCustomer createCustomer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates");
    }

    @Test
    public void test_find_all_success() throws Exception {
        final Collection<Customer> customers = from(Customer.class).gimme(1, "customer");
        when(findCustomer.all()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cliente Petz"))
                .andExpect(jsonPath("$[0].email").value("teste@teste.com.br"))
                .andExpect(jsonPath("$[0].gender").value("M"))
                .andExpect(jsonPath("$[0].cpf").value("12345678909"))
                .andExpect(jsonPath("$[0].phones[0].type").value(PhoneTypeContract.MOBILE.name()))
                .andExpect(jsonPath("$[0].addresses[0].state").value("SP"));
    }

    @Test
    public void test_find_by_id_success() throws Exception {
        final Customer customer = from(Customer.class).gimme("customer");
        when(findCustomer.byId(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/customers/{idCustomer}", 1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cliente Petz"))
                .andExpect(jsonPath("$.email").value("teste@teste.com.br"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.cpf").value("12345678909"))
                .andExpect(jsonPath("$.phones[0].type").value(PhoneTypeContract.MOBILE.name()))
                .andExpect(jsonPath("$.addresses[0].state").value("SP"));
    }

    @Test
    public void test_find_by_id_not_found() throws Exception {
        when(findCustomer.byId(anyLong())).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/customers/{idCustomer}", 1l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_delete_by_id_success() throws Exception {
        doNothing().when(deleteCustomer).execute(anyLong());

        mockMvc.perform(delete("/customers/{idCustomer}", 1l))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_delete_by_id_not_found() throws Exception {
        doThrow(ResourceNotFoundException.class).when(deleteCustomer).execute(anyLong());

        mockMvc.perform(delete("/customers/{idCustomer}", 1l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_update_success() throws Exception {
        final CustomerContract customerContract = from(CustomerContract.class).gimme("customer-contract");

        final Customer customer = from(Customer.class).gimme("customer");
        when(updateCustomer.execute(any())).thenReturn(customer);

        mockMvc.perform(put("/customers/{idCustomer}", 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(customerContract)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cliente Petz"))
                .andExpect(jsonPath("$.email").value("teste@teste.com.br"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.cpf").value("12345678909"))
                .andExpect(jsonPath("$.phones[0].type").value(PhoneTypeContract.MOBILE.name()))
                .andExpect(jsonPath("$.addresses[0].state").value("SP"));
    }

    @Test
    public void test_update_by_id_not_found() throws Exception {
        final CustomerContract customerContract = from(CustomerContract.class).gimme("customer-contract");
        when(updateCustomer.execute(any())).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(put("/customers/{idCustomer}", 4l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(customerContract)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_create_success() throws Exception {
        final CustomerContract customerContract = from(CustomerContract.class).gimme("customer-contract");

        final Customer customer = from(Customer.class).gimme("customer");
        when(createCustomer.execute(any())).thenReturn(customer);

        mockMvc.perform(post("/customers", 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(customerContract)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Cliente Petz"))
                .andExpect(jsonPath("$.email").value("teste@teste.com.br"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.cpf").value("12345678909"))
                .andExpect(jsonPath("$.phones[0].type").value(PhoneTypeContract.MOBILE.name()))
                .andExpect(jsonPath("$.addresses[0].state").value("SP"));
    }
}
