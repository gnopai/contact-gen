package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.Odds;
import com.gnopai.contactgen.statistics.address.Directional;

public class PreDirectionalGenerator implements FieldGenerator {
    static final Odds ODDS = Odds.of(3).in(4);
    private final DirectionalGenerator directionalGenerator;

    @Inject
    public PreDirectionalGenerator(DirectionalGenerator directionalGenerator) {
        this.directionalGenerator = directionalGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        if (contactData.hasPostDirectional()) {
            return contactData;
        }
        Directional preDirectional = directionalGenerator.generate(ODDS);
        return contactData.withPreDirectional(preDirectional);
    }
}