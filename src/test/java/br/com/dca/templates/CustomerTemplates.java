package br.com.dca.templates;

import br.com.dca.domains.Address;
import br.com.dca.domains.Customer;
import br.com.dca.domains.Phone;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;

public class CustomerTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Customer.class).addTemplate("customer", new Rule() {{
            add("id", 1l);
            add("name", "Cliente Petz");
            add("email", "teste@teste.com.br");
            add("gender", "M");
            add("dateOfBirth", LocalDate.of(1991, 2, 2));
            add("cpf", "12345678909");
            add("phones", has(1).of(Phone.class, "phone"));
            add("addresses", has(1).of(Address.class, "address"));
        }});
    }
}