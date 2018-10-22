package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.generator.address.*;
import com.gnopai.contactgen.generator.dob.DateOfBirthGenerator;
import com.gnopai.contactgen.generator.gender.GenderGenerator;
import com.gnopai.contactgen.generator.name.FirstNameGenerator;
import com.gnopai.contactgen.generator.name.LastNameGenerator;
import com.gnopai.contactgen.generator.name.MiddleInitialGenerator;
import com.gnopai.contactgen.generator.phone.AreaCodeGenerator;
import com.gnopai.contactgen.generator.phone.PhoneExchangeGenerator;
import com.gnopai.contactgen.generator.phone.PhoneLineGenerator;

import javax.inject.Inject;
import java.util.List;

/** This class wraps the collected generators and maintains the order they should run in. */
public class FieldGenerators {
    private final List<FieldGenerator> fieldGenerators;

    @Inject
    public FieldGenerators(GenderGenerator genderGenerator,
                           DateOfBirthGenerator dateOfBirthGenerator,
                           FirstNameGenerator firstNameGenerator,
                           MiddleInitialGenerator middleInitialGenerator,
                           LastNameGenerator lastNameGenerator,
                           HouseNumberGenerator houseNumberGenerator,
                           PreDirectionalGenerator preDirectionalGenerator,
                           StreetNameGenerator streetNameGenerator,
                           StreetSuffixGenerator streetSuffixGenerator,
                           PostDirectionalGenerator postDirectionalGenerator,
                           UnitGenerator unitGenerator,
                           StateGenerator stateGenerator,
                           CityGenerator cityGenerator,
                           ZipCodeGenerator zipCodeGenerator,
                           AreaCodeGenerator areaCodeGenerator,
                           PhoneExchangeGenerator phoneExchangeGenerator,
                           PhoneLineGenerator phoneLineGenerator) {

        // Note that the order the generators run in is maintained here.
        fieldGenerators = List.of(
                genderGenerator,
                dateOfBirthGenerator,
                firstNameGenerator, // requires gender and DOB
                middleInitialGenerator, // requires gender and DOB
                lastNameGenerator,
                houseNumberGenerator,
                preDirectionalGenerator,
                streetNameGenerator,
                streetSuffixGenerator,
                postDirectionalGenerator,
                unitGenerator,
                stateGenerator,
                cityGenerator, // requires state
                zipCodeGenerator, // requires city
                areaCodeGenerator, // requires zip
                phoneExchangeGenerator,
                phoneLineGenerator);
    }

    public List<FieldGenerator> get() {
        return fieldGenerators;
    }
}
