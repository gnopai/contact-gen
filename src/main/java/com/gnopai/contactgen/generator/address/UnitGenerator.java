package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.Odds;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.UnitType;

public class UnitGenerator implements FieldGenerator {
    static final Odds ODDS = Odds.of(1).in(10);
    static final int UNIT_NUMBER_MIN = 1;
    static final int UNIT_NUMBER_MAX = 100;
    private final RandomGenerator randomGenerator;

    @Inject
    public UnitGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        if (randomGenerator.selectChance(ODDS)) {
            return contactData.withUnit(generateUnit());
        }
        return contactData;
    }

    private Unit generateUnit() {
        UnitType unitType = randomGenerator.selectNormallyDistributedListItem(UnitType.valuesAsList());
        int unitNumber = randomGenerator.selectInteger(UNIT_NUMBER_MIN, UNIT_NUMBER_MAX);
        return new Unit(String.valueOf(unitNumber), unitType);
    }
}
