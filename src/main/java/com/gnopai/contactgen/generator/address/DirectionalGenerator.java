package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.random.Odds;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.Directional;

public class DirectionalGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public DirectionalGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Directional generate(Odds odds) {
        if (randomGenerator.selectChance(odds)) {
            return randomGenerator.selectUniformlyDistributedListItem(Directional.valuesAsList());
        }
        return null;
    }

}
