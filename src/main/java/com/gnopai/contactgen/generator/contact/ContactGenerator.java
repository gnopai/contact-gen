package com.gnopai.contactgen.generator.contact;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.ContactTransformer;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.statistics.ContactStatistics;

import java.util.List;
import java.util.Queue;

import static com.google.common.collect.Lists.newLinkedList;

public class ContactGenerator {
    private static final int MAX_ITERATIONS = 100;
    private final ContactTransformer contactTransformer;
    private final List<FieldGenerator> fieldGenerators;

    @Inject
    public ContactGenerator(List<FieldGenerator> fieldGenerators, ContactTransformer contactTransformer) {
        this.fieldGenerators = fieldGenerators;
        this.contactTransformer = contactTransformer;
    }

    public Contact generateContact(ContactStatistics contactStatistics) {
        int counter = 1;
        ContactData contactData = ContactData.empty();
        Queue<FieldGenerator> generatorsToRun = newLinkedList(fieldGenerators);

        FieldGenerator fieldGenerator;
        while ((fieldGenerator = generatorsToRun.poll()) != null) {
            if (fieldGenerator.requiresMoreData(contactData)) {
                // waiting for more data, put back on end of queue for later
                generatorsToRun.add(fieldGenerator);
            } else {
                contactData = fieldGenerator.generate(contactStatistics, contactData);
            }

            if (counter > MAX_ITERATIONS) {
                throw new IllegalStateException("Bailing out of potentially infinite field generator loop after " + MAX_ITERATIONS + " iterations");
            }
            counter++;
        }

        return contactTransformer.transform(contactData);
    }
}
