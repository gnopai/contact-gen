package com.gnopai.contactgen.generator.gender;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.Chance;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.Gender;
import com.gnopai.contactgen.statistics.ContactStatistics;

import static com.gnopai.contactgen.model.Gender.FEMALE;
import static com.gnopai.contactgen.model.Gender.MALE;

public class GenderGenerator implements FieldGenerator {
    static final Chance CHANCE_OF_BEING_FEMALE = Chance.of(1).in(2);
    private final RandomGenerator randomGenerator;

    @Inject
    public GenderGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        Gender gender = randomGenerator.selectChance(CHANCE_OF_BEING_FEMALE) ? FEMALE : MALE;
        return contactData.withGender(gender);
    }
}
