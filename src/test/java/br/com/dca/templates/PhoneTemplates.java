package br.com.dca.templates;

import br.com.dca.domains.Phone;
import br.com.dca.domains.PhoneType;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PhoneTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Phone.class).addTemplate("phone", new Rule() {{
            add("id", 1l);
            add("areaCode", "11");
            add("number", "998765432");
            add("type", PhoneType.MOBILE);
        }});
    }
}