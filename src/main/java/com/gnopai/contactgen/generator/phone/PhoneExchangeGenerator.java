package com.gnopai.contactgen.generator.phone;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

public class PhoneExchangeGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public PhoneExchangeGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String phoneExchange = generatePhoneExchange();
        return contactData.withPhoneExchange(phoneExchange);
    }

    private String generatePhoneExchange() {
        String firstDigit = String.valueOf(randomGenerator.selectInteger(2, 10));
        String secondDigit = String.valueOf(randomGenerator.selectInteger(0, 10));
        String thirdDigit = String.valueOf(randomGenerator.selectInteger(0, 10));
        return firstDigit + secondDigit + thirdDigit;
    }
}
