package br.com.dca.templates;

import br.com.dca.gateways.http.contracts.AddressContract;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class AddressContractTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(AddressContract.class).addTemplate("address-contract", new Rule() {{
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
