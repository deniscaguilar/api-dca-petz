package br.com.dca.gateways.http.converters;

import br.com.dca.domains.Phone;
import br.com.dca.domains.PhoneType;
import br.com.dca.gateways.http.contracts.PhoneContract;
import br.com.dca.gateways.http.contracts.PhoneTypeContract;
import org.springframework.beans.BeanUtils;

public class PhoneConverter {

    public static Phone convertFromContractToDomain(final PhoneContract phoneContract) {
        final Phone phone = new Phone();
        BeanUtils.copyProperties(phoneContract, phone, "type");
        phone.setType(PhoneType.valueOf(phoneContract.getType().name()));
        return phone;
    }

    public static PhoneContract convertFromDomainToContract(final Phone phone) {
        final PhoneContract phoneContract = new PhoneContract();
        BeanUtils.copyProperties(phone, phoneContract, "type");
        phoneContract.setType(PhoneTypeContract.valueOf(phone.getType().name()));
        return phoneContract;
    }

}
