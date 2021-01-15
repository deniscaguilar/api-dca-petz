package br.com.dca.gateways.http.converters;

import br.com.dca.domains.Address;
import br.com.dca.gateways.http.contracts.AddressContract;
import org.springframework.beans.BeanUtils;

public class AddressConverter {

    public static Address convertFromContractToDomain(final AddressContract addressContract) {
        final Address address = new Address();
        BeanUtils.copyProperties(addressContract, address);
        return address;
    }

    public static AddressContract convertFromDomainToContract(final Address address) {
        final AddressContract addressContract = new AddressContract();
        BeanUtils.copyProperties(address, addressContract);
        return addressContract;
    }

}
