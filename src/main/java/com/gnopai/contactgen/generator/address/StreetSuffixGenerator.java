package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.Odds;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.StreetSuffix;

public class StreetSuffixGenerator implements FieldGenerator {
    static final Odds ODDS_OF_LONG_STREET_NAME = Odds.of(1).in(4);
    private final RandomGenerator randomGenerator;

    @Inject
    public StreetSuffixGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        return contactData.withStreetSuffix(generateStreetSuffix());
    }

    private String generateStreetSuffix() {
        StreetSuffix streetSuffix = randomGenerator.selectNormallyDistributedListItem(StreetSuffix.valuesAsList());
        return randomGenerator.selectChance(ODDS_OF_LONG_STREET_NAME) ? streetSuffix.getLongName() : streetSuffix.getShortName();
    }
}
