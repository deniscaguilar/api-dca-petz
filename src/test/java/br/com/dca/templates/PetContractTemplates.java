package br.com.dca.templates;

import br.com.dca.gateways.http.contracts.PetContract;
import br.com.dca.gateways.http.contracts.PetTypeContract;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;

public class PetContractTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PetContract.class).addTemplate("pet", new Rule() {{
            add("name", "Duque");
            add("gender", "M");
            add("type", PetTypeContract.DOG);
            add("description", "Cachorro de grande porte");
            add("dateLastVaccination", LocalDate.of(2020, 3, 15));
            add("castrated", Boolean.TRUE);
        }});

    }

}
