package com.gnopai.contactgen.generator.dob;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.Range;
import com.google.inject.Inject;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public class DateOfBirthGenerator implements FieldGenerator {

    private final RandomGenerator randomGenerator;

    @Inject
    public DateOfBirthGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        LocalDate dateOfBirth = generateDateOfBirth(contactStatistics);
        return contactData.withDateOfBirth(dateOfBirth);
    }

    private LocalDate generateDateOfBirth(ContactStatistics contactStatistics) {
        Range ageRange = randomGenerator.selectWeightDistributedListItem(contactStatistics.getAgeRanges());
        int age = randomGenerator.selectInteger(ageRange);

        LocalDate dateOfBirth = LocalDate.now().minus(age, YEARS);
        dateOfBirth = dateOfBirth.withMonth(randomGenerator.selectInteger(1, 13));
        int daysInMonth = dateOfBirth.lengthOfMonth();
        return dateOfBirth.withDayOfMonth(randomGenerator.selectInteger(1, daysInMonth + 1));
    }

}
