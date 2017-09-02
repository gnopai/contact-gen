package com.gnopai.contactgen.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Address {
    String city;
    State state;
    String zipCode;
    String street;
}
