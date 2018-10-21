package com.gnopai.contactgen.generator.address;

import com.google.inject.Inject;
import com.gnopai.contactgen.generator.random.Chance;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.Directional;

public class DirectionalGenerator {
    private final RandomGenerator randomGenerator;

    @Inject
    public DirectionalGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Directional generate(Chance chance) {
        if (randomGenerator.selectChance(chance)) {
            return randomGenerator.selectUniformlyDistributedListItem(Directional.valuesAsList());
        }
        return null;
    }

}
