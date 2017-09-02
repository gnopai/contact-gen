package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.RandomGenerator;

import static java.lang.Math.max;

public class HouseNumberGenerator implements FieldGenerator {
    static final int MIN_LENGTH = 1;
    static final int MAX_LENGTH = 6;
    private final RandomGenerator randomGenerator;

    @Inject
    public HouseNumberGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        return contactData.withHouseNumber(generateHouseNumber());
    }

    private String generateHouseNumber() {
        int length = randomGenerator.selectInteger(MIN_LENGTH, MAX_LENGTH);
        int minimum = (int) Math.pow(10 , length - 1);
        int maximum = (int) Math.pow(10, length);
        int number = randomGenerator.selectInteger(max(minimum, 1), maximum);
        return String.valueOf(number);
    }
}
