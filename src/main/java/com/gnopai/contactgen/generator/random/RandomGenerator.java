package com.gnopai.contactgen.generator.random;

import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;

import java.util.List;

public interface RandomGenerator {
    <T> T selectNormallyDistributedListItem(List<T> items);
    <T> T selectWeightDistributedListItem(WeightedList<T> items);
    <T> T selectUniformlyDistributedListItem(List<T> items);

    /** Returns a random integer between low (inclusive) and high (exclusive). */
    int selectInteger(int low, int high);

    /** Returns a random integer between low (inclusive) and high (exclusive). */
    int selectInteger(Range range);

    boolean selectChance(Odds odds);
}
