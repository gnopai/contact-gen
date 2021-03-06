package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.Chance;
import com.gnopai.contactgen.statistics.address.Directional;

public class PostDirectionalGenerator implements FieldGenerator {
    static final Chance CHANCE = Chance.of(1).in(30);
    private final DirectionalGenerator directionalGenerator;

    @Inject
    public PostDirectionalGenerator(DirectionalGenerator directionalGenerator) {
        this.directionalGenerator = directionalGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        if (contactData.hasPreDirectional()) {
            return contactData;
        }
        Directional postDirectional = directionalGenerator.generate(CHANCE);
        return contactData.withPostDirectional(postDirectional);
    }
}
