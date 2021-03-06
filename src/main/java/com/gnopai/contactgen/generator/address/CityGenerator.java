package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class CityGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public CityGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String city = generateCity(contactStatistics, contactData.getState());
        return contactData.withCity(city);
    }

    private String generateCity(ContactStatistics contactStatistics, State state) {
        return randomGenerator.selectNormallyDistributedListItem(contactStatistics.getCitiesForState(state));
    }

    @Override
    public boolean requiresMoreData(ContactData contactData) {
        return !contactData.hasState();
    }
}
