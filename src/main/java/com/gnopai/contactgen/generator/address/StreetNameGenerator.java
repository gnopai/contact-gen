package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class StreetNameGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public StreetNameGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String streetName = generateStreetName(contactStatistics);
        return contactData.withStreetName(streetName);
    }

    private String generateStreetName(ContactStatistics contactStatistics) {
        return randomGenerator.selectUniformlyDistributedListItem(contactStatistics.getStreetNames());
    }
}
