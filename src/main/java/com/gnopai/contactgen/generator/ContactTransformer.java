package com.gnopai.contactgen.generator;

import com.google.common.base.Joiner;
import com.gnopai.contactgen.model.Address;
import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.model.Name;
import com.gnopai.contactgen.model.Phone;

import static com.google.common.collect.Lists.newArrayList;

public class ContactTransformer {

    public Contact transform(ContactData contactData) {
        return Contact.builder()
                .gender(contactData.getGender())
                .dateOfBirth(contactData.getDateOfBirth())
                .ssn(contactData.getSsn())
                .name(createName(contactData))
                .address(createAddress(contactData))
                .phone(createPhone(contactData))
                .build();
    }

    private Name createName(ContactData contactData) {
        return Name.builder()
                .givenName(contactData.getFirstName())
                .middleInitial(contactData.getMiddleInitial())
                .surname(contactData.getLastName())
                .build();
    }

    private Address createAddress(ContactData contactData) {
        return Address.builder()
                .street(buildStreetAddress(contactData))
                .city(contactData.getCity())
                .state(contactData.getState())
                .zipCode(contactData.getZipCode())
                .build();
    }

    private String buildStreetAddress(ContactData contactData) {
        return Joiner.on(" ").skipNulls().join(newArrayList(
                contactData.getHouseNumber(),
                contactData.getPreDirectionalShortName(),
                contactData.getStreetName(),
                contactData.getStreetSuffix(),
                contactData.getPostDirectionalShortName(),
                contactData.getUnitAsString()
        ));
    }

    private Phone createPhone(ContactData contactData) {
        return Phone.builder()
                .areaCode(contactData.getPhoneAreaCode())
                .exchange(contactData.getPhoneExchange())
                .line(contactData.getPhoneLine())
                .build();
    }

}
