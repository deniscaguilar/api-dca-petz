package br.com.dca.gateways.http.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneContract {

    private String areaCode;

    private String number;

    private PhoneTypeContract type;

}
