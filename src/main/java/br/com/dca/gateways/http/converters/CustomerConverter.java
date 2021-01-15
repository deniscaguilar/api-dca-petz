package br.com.dca.gateways.http.converters;

import br.com.dca.domains.Customer;
import br.com.dca.gateways.http.contracts.CustomerContract;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class CustomerConverter {

    public static Customer convertFromContractToDomain(final Long idCustomer, final CustomerContract customerContract) {
        final Customer customer = new Customer();
        BeanUtils.copyProperties(customerContract, customer, "phone", "addresses");
        customer.setId(idCustomer);
        customer.setPhones(customerContract.getPhones()
                .stream()
                .map(phoneContract -> PhoneConverter.convertFromContractToDomain(phoneContract))
                .collect(Collectors.toList()));

        customer.setAddresses(customerContract.getAddresses()
                .stream()
                .map(addressContract -> AddressConverter.convertFromContractToDomain(addressContract))
                .collect(Collectors.toList()));

        return customer;
    }

    public static Customer convertFromContractToDomain(final CustomerContract customerContract) {
        final Customer customer = new Customer();
        BeanUtils.copyProperties(customerContract, customer, "phone", "addresses");
        customer.setPhones(customerContract.getPhones()
                .stream()
                .map(phoneContract -> PhoneConverter.convertFromContractToDomain(phoneContract))
                .collect(Collectors.toList()));

        customer.setAddresses(customerContract.getAddresses()
                .stream()
                .map(addressContract -> AddressConverter.convertFromContractToDomain(addressContract))
                .collect(Collectors.toList()));

        return customer;
    }

    public static CustomerContract convertFromDomainToContract(final Customer customer) {
        final CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(customer, customerContract, "phone", "addresses");
        customerContract.setPhones(customer.getPhones()
                .stream()
                .map(phone -> PhoneConverter.convertFromDomainToContract(phone))
                .collect(Collectors.toList()));

        customerContract.setAddresses(customer.getAddresses()
                .stream()
                .map(address -> AddressConverter.convertFromDomainToContract(address))
                .collect(Collectors.toList()));

        return customerContract;
    }

}
