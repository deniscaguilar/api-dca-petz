package br.com.dca.templates;

import br.com.dca.gateways.http.contracts.AddressContract;
import br.com.dca.gateways.http.contracts.CustomerContract;
import br.com.dca.gateways.http.contracts.PhoneContract;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;

public class CustomerContractTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(CustomerContract.class).addTemplate("customer-contract", new Rule() {{
            add("name", "Cliente Petz");
            add("email", "teste@teste.com.br");
            add("gender", "M");
            add("dateOfBirth", LocalDate.of(1991, 2, 2));
            add("cpf", "12345678909");
            add("phones", has(1).of(PhoneContract.class, "phone-contract"));
            add("addresses", has(1).of(AddressContract.class, "address-contract"));
        }});
    }
}
