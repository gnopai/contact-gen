package com.gnopai.contactgen.generator.name;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class LastNameGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public LastNameGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String lastName = generateLastName(contactStatistics);
        return contactData.withLastName(lastName);
    }

    private String generateLastName(ContactStatistics contactStatistics) {
        return randomGenerator.selectWeightDistributedListItem(contactStatistics.getLastNames());
    }

}
