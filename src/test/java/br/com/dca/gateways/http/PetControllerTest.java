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

import br.com.dca.domains.Pet;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.http.contracts.PetContract;
import br.com.dca.gateways.http.contracts.PetTypeContract;
import br.com.dca.usecases.CreatePet;
import br.com.dca.usecases.DeletePet;
import br.com.dca.usecases.FindPet;
import br.com.dca.usecases.UpdatePet;
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

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @MockBean
    private FindPet findPet;

    @MockBean
    private DeletePet deletePet;

    @MockBean
    private UpdatePet updatePet;

    @MockBean
    private CreatePet createPet;

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
        final Collection<Pet> pets = from(Pet.class).gimme(1, "pet-type-dog");
        when(findPet.all()).thenReturn(pets);

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Duque"))
                .andExpect(jsonPath("$[0].gender").value("M"))
                .andExpect(jsonPath("$[0].type").value(PetTypeContract.DOG.name()))
                .andExpect(jsonPath("$[0].description").value("Cachorro de grande porte"))
                .andExpect(jsonPath("$[0].castrated").value(Boolean.TRUE));
    }

    @Test
    public void test_find_by_id_success() throws Exception {
        final Pet pet = from(Pet.class).gimme("pet-type-dog");
        when(findPet.byId(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/pets/{idPet}", 1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Duque"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.type").value(PetTypeContract.DOG.name()))
                .andExpect(jsonPath("$.description").value("Cachorro de grande porte"))
                .andExpect(jsonPath("$.castrated").value(Boolean.TRUE));
    }

    @Test
    public void test_find_by_id_not_found() throws Exception {
        when(findPet.byId(anyLong())).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/pets/{idPet}", 1l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_delete_by_id_success() throws Exception {
        doNothing().when(deletePet).execute(anyLong());

        mockMvc.perform(delete("/pets/{idPet}", 1l))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_delete_by_id_not_found() throws Exception {
        doThrow(ResourceNotFoundException.class).when(deletePet).execute(anyLong());

        mockMvc.perform(delete("/pets/{idPet}", 1l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_update_success() throws Exception {
        final PetContract petContract = from(PetContract.class).gimme("pet");

        final Pet pet = from(Pet.class).gimme("pet-type-dog");
        when(updatePet.execute(any())).thenReturn(pet);

        mockMvc.perform(put("/pets/{idPet}", 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(petContract)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Duque"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.type").value(PetTypeContract.DOG.name()))
                .andExpect(jsonPath("$.description").value("Cachorro de grande porte"))
                .andExpect(jsonPath("$.castrated").value(Boolean.TRUE));
    }

    @Test
    public void test_update_by_id_not_found() throws Exception {
        final PetContract petContract = from(PetContract.class).gimme("pet");
        when(updatePet.execute(any())).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(put("/pets/{idPet}", 4l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(petContract)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_create_success() throws Exception {
        final PetContract petContract = from(PetContract.class).gimme("pet");

        final Pet pet = from(Pet.class).gimme("pet-type-dog");
        when(createPet.execute(any())).thenReturn(pet);

        mockMvc.perform(post("/pets", 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(petContract)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Duque"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.type").value(PetTypeContract.DOG.name()))
                .andExpect(jsonPath("$.description").value("Cachorro de grande porte"))
                .andExpect(jsonPath("$.castrated").value(Boolean.TRUE));
    }

}
