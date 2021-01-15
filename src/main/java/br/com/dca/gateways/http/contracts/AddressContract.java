package br.com.dca.gateways.http.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressContract {

    private String name;

    private String zipCode;

    private String streetName;

    private String streetNumber;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;
    
    private String reference;

}
