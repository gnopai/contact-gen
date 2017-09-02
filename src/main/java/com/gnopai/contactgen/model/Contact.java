package com.gnopai.contactgen.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Contact {
    Name name;
    Address address;
    Phone phone;
    LocalDate dateOfBirth;
    String ssn;
    Gender gender;
}
