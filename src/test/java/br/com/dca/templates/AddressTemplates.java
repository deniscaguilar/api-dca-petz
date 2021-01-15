package br.com.dca.templates;

import br.com.dca.domains.Address;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class AddressTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Address.class).addTemplate("address", new Rule() {{
            add("id", 1l);
            add("name", "Residencial");
            add("zipCode", "097750321");
            add("streetName", "Rua dos Pets");
            add("streetNumber", "243");
            add("complement", "Torre A - apto 432");
            add("neighborhood", "Centro");
            add("city", "São Paulo");
            add("state", "SP");
            add("reference", "Loja Petz");
        }});
    }
}