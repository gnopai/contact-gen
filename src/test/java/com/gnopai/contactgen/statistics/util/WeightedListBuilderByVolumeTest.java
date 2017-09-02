package com.gnopai.contactgen.statistics.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class WeightedListBuilderByVolumeTest {

    @Test
    public void test() {
        WeightedListBuilderByVolume<Integer> builder = new WeightedListBuilderByVolume<>(10);
        builder.addItem(0, 5); // 5 of 20, 0.25
        builder.addItem(1, 10); // 10 of 20, 0.5
        builder.addItem(2, 2); // 2 of 20, 0.1
        builder.addItem(3, 1); // 1 of 20, 0.05
        builder.addItem(4, 2); // 2 of 20, 0.1
        WeightedList<Integer> weightedList = builder.build();

        assertListItem(weightedList, 0, 0, new BigDecimal("0.25"));
        assertListItem(weightedList, 1, 1, new BigDecimal("0.50"));
        assertListItem(weightedList, 2, 2, new BigDecimal("0.10"));
        assertListItem(weightedList, 3, 3, new BigDecimal("0.05"));
        assertListItem(weightedList, 4, 4, new BigDecimal("0.10"));
    }

    private <T> void assertListItem(WeightedList<T> weightedList, int index, T value, BigDecimal weight) {
        assertEquals(value, weightedList.get(index));
        assertEquals(0, weight.compareTo(weightedList.getWeight(index)));
    }
}