package com.gnopai.contactgen.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Name {
    String givenName;
    String middleInitial;
    String surname;
}
