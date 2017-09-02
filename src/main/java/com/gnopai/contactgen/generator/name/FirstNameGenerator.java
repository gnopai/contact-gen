package com.gnopai.contactgen.generator.name;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.google.inject.Inject;

public class FirstNameGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public FirstNameGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String firstName = generateFirstName(contactStatistics, contactData);
        return contactData.withFirstName(firstName);
    }

    String generateFirstName(ContactStatistics contactStatistics, ContactData contactData) {
        WeightedList<String> firstNameList = contactStatistics.getFirstNameList(contactData.getGender(), contactData.getDecadeOfBirth());
        return randomGenerator.selectWeightDistributedListItem(firstNameList);
    }

    @Override
    public boolean requiresMoreData(ContactData contactData) {
        return !contactData.hasDateOfBirth() || !contactData.hasGender();
    }
}
