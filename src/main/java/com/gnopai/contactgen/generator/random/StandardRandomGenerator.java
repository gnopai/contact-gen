package com.gnopai.contactgen.generator.random;

import com.google.inject.Inject;
import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class StandardRandomGenerator implements RandomGenerator {
    private final Random random;

    @Inject
    public StandardRandomGenerator(Random random) {
        this.random = random;
    }

    @Override
    public <T> T selectNormallyDistributedListItem(List<T> items) {
        int mean = 0; // use 0 as a mean and just absolute-value it later, since it's symmetrical
        int deviation = items.size() / 3;
        int index = (int) ((random.nextGaussian() * deviation) + mean);
        index = Math.abs(index);
        if (index >= items.size()) {
            // there's a remaining 0.03% chance of this sort of outlier -- just lump it in the middle
            index = 0;
        }
        return items.get(index);
    }

    @Override
    public <T> T selectWeightDistributedListItem(WeightedList<T> items) {
        BigDecimal value = new BigDecimal(random.nextDouble());
        BigDecimal weightSum = new BigDecimal(0).setScale(10, RoundingMode.HALF_UP);
        for (WeightedList.Entry<T> entry : items.getEntries()) {
            weightSum = weightSum.add(entry.getWeight());
            if (value.compareTo(weightSum) < 0) {
                return entry.getValue();
            }
        }
        return items.get(0);
    }

    @Override
    public <T> T selectUniformlyDistributedListItem(List<T> items) {
        int randomIndex = random.nextInt(items.size());
        return items.get(randomIndex);
    }

    @Override
    public int selectInteger(int low, int high) {
        int randomInt = random.nextInt(high - low);
        return low + randomInt;
    }

    @Override
    public int selectInteger(Range range) {
        return selectInteger(range.getLow(), range.getHigh());
    }

    @Override
    public boolean selectChance(Chance chance) {
        return chance.getX() > random.nextInt(chance.getY());
    }

}
