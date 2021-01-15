package br.com.dca.templates;

import br.com.dca.domains.Pet;
import br.com.dca.domains.PetType;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;

public class PetTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Pet.class).addTemplate("pet-type-dog", new Rule() {{
            add("id", 1l);
            add("name", "Duque");
            add("gender", "M");
            add("type", PetType.DOG);
            add("description", "Cachorro de grande porte");
            add("dateLastVaccination", LocalDate.of(2020, 3, 15));
            add("castrated", Boolean.TRUE);
        }});

        Fixture.of(Pet.class).addTemplate("pet-type-cat", new Rule() {{
            add("id", 2l);
            add("name", "Flor");
            add("gender", "F");
            add("type", PetType.CAT);
            add("description", "Gato disponível para adoção");
            add("dateLastVaccination", LocalDate.of(2020, 3, 15));
            add("castrated", Boolean.FALSE);
        }});
    }

}
