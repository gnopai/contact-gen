package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.generator.address.Unit;
import com.gnopai.contactgen.model.Address;
import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.model.Name;
import com.gnopai.contactgen.model.Phone;
import org.junit.Test;

import java.time.LocalDate;

import static com.gnopai.contactgen.statistics.address.Directional.NORTHWEST;
import static com.gnopai.contactgen.statistics.address.UnitType.APARTMENT;
import static com.gnopai.contactgen.model.Gender.MALE;
import static com.gnopai.contactgen.model.State.OREGON;
import static org.junit.Assert.assertEquals;

public class ContactTransformerTest {

    @Test
    public void test() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.builder()
                .gender(MALE)
                .ssn("ssn")
                .dateOfBirth(LocalDate.MAX)
                .firstName("Bob")
                .middleInitial("B")
                .lastName("Bobson")
                .houseNumber("111")
                .preDirectional(NORTHWEST)
                .streetName("Main")
                .streetSuffix("St")
                .unit(new Unit("666", APARTMENT))
                .city("Portland")
                .state(OREGON)
                .zipCode("97201")
                .phoneAreaCode("555")
                .phoneExchange("666")
                .phoneLine("7777")
                .build();

        // WHEN
        Contact contact = new ContactTransformer().transform(contactData);

        // THEN
        Contact expectedContact = Contact.builder()
                .gender(MALE)
                .ssn("ssn")
                .dateOfBirth(LocalDate.MAX)
                .name(Name.builder().givenName("Bob").middleInitial("B").surname("Bobson").build())
                .address(Address.builder().street("111 NW Main St Apt 666").city("Portland").state(OREGON).zipCode("97201").build())
                .phone(Phone.builder().areaCode("555").exchange("666").line("7777").build())
                .build();
        assertEquals(expectedContact, contact);
    }

}