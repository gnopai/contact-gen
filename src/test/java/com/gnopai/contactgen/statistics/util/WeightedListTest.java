package com.gnopai.contactgen.statistics.util;

import org.junit.Test;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class WeightedListTest {

    @Test
    public void testBuildAndGet() {
        // GIVEN
        WeightedList<String> weightedList = new WeightedList<>();

        // WHEN
        String firstItem = "one";
        BigDecimal firstWeight = new BigDecimal("0.1");
        weightedList.add(firstItem, firstWeight);

        String secondItem = "two";
        BigDecimal secondWeight = new BigDecimal("0.2");
        weightedList.add(secondItem, secondWeight);

        // THEN
        assertEquals(firstItem, weightedList.get(0));
        assertEquals(0, firstWeight.compareTo(weightedList.getWeight(0)));

        assertEquals(secondItem, weightedList.get(1));
        assertEquals(0, secondWeight.compareTo(weightedList.getWeight(1)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetEntries_throwsExceptionOnModification() {
        WeightedList<String> weightedList = new WeightedList<>();
        weightedList.getEntries().add(1, null);
    }

    @Test
    public void testToList() {
        // GIVEN
        WeightedList<String> weightedList = new WeightedList<>();

        // WHEN
        weightedList.add("one", new BigDecimal("0.1"));
        weightedList.add("two", new BigDecimal("0.5"));
        weightedList.add("three", new BigDecimal("0.3"));

        // THEN
        assertEquals(newArrayList("one", "two", "three"), weightedList.toList());
    }

}
