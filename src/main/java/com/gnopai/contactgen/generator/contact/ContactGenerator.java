package com.gnopai.contactgen.generator.contact;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.ContactTransformer;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

import java.util.List;

public class ContactGenerator {
    private final ContactTransformer contactTransformer;
    private final List<FieldGenerator> fieldGenerators;

    @Inject
    public ContactGenerator(List<FieldGenerator> fieldGenerators, ContactTransformer contactTransformer) {
        this.fieldGenerators = fieldGenerators;
        this.contactTransformer = contactTransformer;
    }

    public Contact generateContact(ContactStatistics contactStatistics) {
        ContactData contactData = ContactData.empty();
        for (FieldGenerator fieldGenerator : fieldGenerators) {
            if (fieldGenerator.requiresMoreData(contactData)) {
                throw new IllegalStateException("Missing required data for generator " + fieldGenerator.getClass() + ". Order issue?");
            }
            contactData = fieldGenerator.generate(contactStatistics, contactData);
        }
        return contactTransformer.transform(contactData);
    }
}
