package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class StateGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public StateGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        State state = generateState(contactStatistics);
        return contactData.withState(state);
    }

    private State generateState(ContactStatistics contactStatistics) {
        return randomGenerator.selectWeightDistributedListItem(contactStatistics.getStates());
    }
}
