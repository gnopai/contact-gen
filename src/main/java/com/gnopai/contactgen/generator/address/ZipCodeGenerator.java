package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

import java.util.List;

public class ZipCodeGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public ZipCodeGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String zipCode = generateZipCode(contactStatistics, contactData.getCity(), contactData.getState());
        return contactData.withZipCode(zipCode);
    }

    private String generateZipCode(ContactStatistics contactStatistics, String city, State state) {
        List<String> zipCodesForCity = contactStatistics.getZipCodesForCity(city, state);
        // TODO what if this is null? (unknown city/state combination)
        return randomGenerator.selectUniformlyDistributedListItem(zipCodesForCity);
    }

    @Override
    public boolean requiresMoreData(ContactData contactData) {
        return !contactData.hasCity() || !contactData.hasState();
    }
}
