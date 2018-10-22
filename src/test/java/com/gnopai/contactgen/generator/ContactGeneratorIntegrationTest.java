package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.AlwaysPickFirstItem;
import com.gnopai.contactgen.generator.address.*;
import com.gnopai.contactgen.generator.dob.DateOfBirthGenerator;
import com.gnopai.contactgen.generator.gender.GenderGenerator;
import com.gnopai.contactgen.generator.name.FirstNameGenerator;
import com.gnopai.contactgen.generator.name.LastNameGenerator;
import com.gnopai.contactgen.generator.name.MiddleInitialGenerator;
import com.gnopai.contactgen.generator.phone.AreaCodeGenerator;
import com.gnopai.contactgen.generator.phone.PhoneExchangeGenerator;
import com.gnopai.contactgen.generator.phone.PhoneLineGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.*;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.address.ZipCodes;
import com.gnopai.contactgen.statistics.name.FirstNames;
import com.gnopai.contactgen.statistics.phone.AreaCodes;
import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.google.common.collect.ArrayListMultimap;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.gnopai.contactgen.model.Gender.FEMALE;
import static com.gnopai.contactgen.model.State.OREGON;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;

public class ContactGeneratorIntegrationTest {
    private ContactGenerator testClass;

    @Before
    public void setUp() {
        RandomGenerator randomGenerator = new AlwaysPickFirstItem();
        FirstNameGenerator firstNameGenerator = new FirstNameGenerator(randomGenerator);
        DirectionalGenerator directionalGenerator = new DirectionalGenerator(randomGenerator);
        FieldGenerators fieldGenerators = new FieldGenerators(
                new GenderGenerator(randomGenerator),
                new DateOfBirthGenerator(randomGenerator),
                firstNameGenerator,
                new MiddleInitialGenerator(randomGenerator, firstNameGenerator),
                new LastNameGenerator(randomGenerator),
                new HouseNumberGenerator(randomGenerator),
                new PreDirectionalGenerator(directionalGenerator),
                new StreetNameGenerator(randomGenerator),
                new StreetSuffixGenerator(randomGenerator),
                new PostDirectionalGenerator(directionalGenerator),
                new UnitGenerator(randomGenerator),
                new StateGenerator(randomGenerator),
                new CityGenerator(randomGenerator),
                new ZipCodeGenerator(randomGenerator),
                new AreaCodeGenerator(randomGenerator),
                new PhoneExchangeGenerator(randomGenerator),
                new PhoneLineGenerator(randomGenerator)
        );
        testClass = new ContactGenerator(fieldGenerators, new ContactTransformer());
    }

    @Test
    public void test() {
        // GIVEN
        int age = 40;
        String city = "Portland";
        State state = OREGON;
        String zipCode = "97201";

        ContactStatistics contactStatistics = ContactStatistics.builder()
                .ageRanges(stubWeightedList(new Range(age, age + 10)))
                .firstNames(new FirstNames(
                        emptyMap(),
                        Map.of(1970, stubWeightedList("Jane"),
                                1980, stubWeightedList("Jane"))
                ))
                .lastNames(stubWeightedList("Doe"))
                .streetNames(List.of("Main"))
                .states(stubWeightedList(state))
                .citiesByState(Map.of(state, List.of(city)))
                .zipCodes(new ZipCodes(
                        stubMultimap(new ZipCodes.CityState(city, state), zipCode))
                )
                .areaCodes(new AreaCodes(
                        stubMultimap(zipCode, "503"),
                        emptyMap()
                ))
                .build();

        // WHEN
        Contact contact = testClass.generateContact(contactStatistics);

        // THEN
        int yearOfBirth = LocalDate.now().minusYears(age).getYear();
        Contact expectedContact = Contact.builder()
                .name(Name.builder()
                        .givenName("Jane")
                        .middleInitial("J") // based on the single first name available
                        .surname("Doe")
                        .build())
                .address(Address.builder()
                        .street("1 N Main Street Apt 1") // first values from all the enums & checks
                        .city(city)
                        .state(state)
                        .zipCode(zipCode)
                        .build())
                .phone(Phone.builder()
                        .areaCode("503")
                        .exchange("200") // minimum valid phone exchange
                        .line("0000")
                        .build())
                .dateOfBirth(LocalDate.of(yearOfBirth, 1, 1))
                .gender(FEMALE)
                .build();
        assertEquals(expectedContact, contact);
    }

    private <T> WeightedList<T> stubWeightedList(T item) {
        WeightedList<T> weightedList = new WeightedList<>();
        weightedList.add(item, new BigDecimal("1.0"));
        return weightedList;
    }

    private <K, V> ArrayListMultimap<K, V> stubMultimap(K key, V value) {
        ArrayListMultimap<K, V> multimap = ArrayListMultimap.create();
        multimap.put(key, value);
        return multimap;
    }

}
