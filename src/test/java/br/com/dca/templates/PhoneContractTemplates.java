package br.com.dca.templates;

import br.com.dca.gateways.http.contracts.PhoneContract;
import br.com.dca.gateways.http.contracts.PhoneTypeContract;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PhoneContractTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PhoneContract.class).addTemplate("phone-contract", new Rule() {{
            add("areaCode", "11");
            add("number", "998765432");
            add("type", PhoneTypeContract.MOBILE);
        }});
    }
}
