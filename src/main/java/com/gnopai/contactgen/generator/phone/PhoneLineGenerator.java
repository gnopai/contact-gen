package com.gnopai.contactgen.generator.phone;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;

import static com.google.common.base.Strings.padStart;

public class PhoneLineGenerator implements FieldGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public PhoneLineGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String phoneLine = String.valueOf(randomGenerator.selectInteger(0, 10000));
        phoneLine = padStart(phoneLine, 4, '0');
        return contactData.withPhoneLine(phoneLine);
    }
}
