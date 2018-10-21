package com.gnopai.contactgen.generator.name;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.Chance;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class MiddleInitialGenerator implements FieldGenerator {
    static final Chance CHANCE_OF_HAVING_MIDDLE_INITIAL = Chance.of(1).in(2);
    private final RandomGenerator randomGenerator;
    private final FirstNameGenerator firstNameGenerator;

    @Inject
    public MiddleInitialGenerator(RandomGenerator randomGenerator, FirstNameGenerator firstNameGenerator) {
        this.randomGenerator = randomGenerator;
        this.firstNameGenerator = firstNameGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        if (!randomGenerator.selectChance(CHANCE_OF_HAVING_MIDDLE_INITIAL)) {
            return contactData;
        }
        String middleInitial = generateMiddleInitial(contactStatistics, contactData);
        return contactData.withMiddleInitial(middleInitial);
    }

    private String generateMiddleInitial(ContactStatistics contactStatistics, ContactData contactData) {
        String name = firstNameGenerator.generateFirstName(contactStatistics, contactData);
        return name.substring(0, 1);
    }

    @Override
    public boolean requiresMoreData(ContactData contactData) {
        return firstNameGenerator.requiresMoreData(contactData);
    }
}
