package com.gnopai.contactgen;

import com.gnopai.contactgen.generator.random.Chance;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;

import java.util.List;

public class AlwaysPickFirstItem implements RandomGenerator {

    @Override
    public <T> T selectNormallyDistributedListItem(List<T> items) {
        return items.get(0);
    }

    @Override
    public <T> T selectWeightDistributedListItem(WeightedList<T> items) {
        return items.get(0);
    }

    @Override
    public <T> T selectUniformlyDistributedListItem(List<T> items) {
        return items.get(0);
    }

    @Override
    public int selectInteger(int low, int high) {
        return low;
    }

    @Override
    public int selectInteger(Range range) {
        return range.getLow();
    }

    @Override
    public boolean selectChance(Chance chance) {
        return true;
    }
}
