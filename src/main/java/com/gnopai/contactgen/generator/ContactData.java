package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.generator.address.Unit;
import com.gnopai.contactgen.model.Gender;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.address.Directional;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Value
@Builder
@Wither
public class ContactData {
    Gender gender;
    LocalDate dateOfBirth;
    String ssn;

    String firstName;
    String middleInitial;
    String lastName;

    String houseNumber;
    Directional preDirectional;
    String streetName;
    String streetSuffix;
    Directional postDirectional;
    Unit unit;

    String city;
    State state;
    String zipCode;

    String phoneAreaCode;
    String phoneExchange;
    String phoneLine;

    public static ContactData empty() {
        return builder().build();
    }

    public boolean hasDateOfBirth() {
        return dateOfBirth != null;
    }

    public boolean hasGender() {
        return gender != null;
    }

    public int getDecadeOfBirth() {
        return (dateOfBirth.getYear() / 10) * 10;
    }

    public boolean hasState() {
        return state != null;
    }

    public boolean hasCity() {
        return city != null;
    }

    public boolean hasZipCode() {
        return zipCode != null;
    }

    public boolean hasPreDirectional() {
        return preDirectional != null;
    }

    public String getPreDirectionalShortName() {
        return preDirectional == null ? null : preDirectional.getShortName();
    }

    public boolean hasPostDirectional() {
        return postDirectional != null;
    }

    public String getPostDirectionalShortName() {
        return postDirectional == null ? null : postDirectional.getShortName();
    }

    public String getUnitAsString() {
        return unit == null ? null : unit.toString();
    }

}
