package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class ContactGenerator {
    private final ContactTransformer contactTransformer;
    private final FieldGenerators fieldGenerators;

    @Inject
    public ContactGenerator(FieldGenerators fieldGenerators, ContactTransformer contactTransformer) {
        this.fieldGenerators = fieldGenerators;
        this.contactTransformer = contactTransformer;
    }

    public Contact generateContact(ContactStatistics contactStatistics) {
        ContactData contactData = ContactData.empty();
        for (FieldGenerator fieldGenerator : fieldGenerators.get()) {
            if (fieldGenerator.requiresMoreData(contactData)) {
                throw new IllegalStateException("Missing required data for generator " + fieldGenerator.getClass() + ". Order issue?");
            }
            contactData = fieldGenerator.generate(contactStatistics, contactData);
        }
        return contactTransformer.transform(contactData);
    }
}
