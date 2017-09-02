package com.gnopai.contactgen.statistics.phone;

import com.google.common.collect.ArrayListMultimap;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.gnopai.contactgen.statistics.util.WeightedListBuilderByVolume;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.gnopai.contactgen.model.State.OREGON;
import static com.gnopai.contactgen.model.State.WASHINGTON;
import static org.junit.Assert.assertEquals;

public class AreaCodeCounterTest {
    private final int scale = 10;

    @Test
    public void test() throws Exception {
        // GIVEN
        AreaCodeCounter testClass = new AreaCodeCounter(scale);

        // WHEN
        testClass.addAll(OREGON, "97201", newArrayList("1", "2", "3"));
        testClass.addAll(OREGON, "97202", newArrayList("2", "3"));
        testClass.addAll(OREGON, "97203", newArrayList("4", "3"));
        testClass.addAll(WASHINGTON, "98601", newArrayList("6"));
        testClass.addAll(WASHINGTON, "98602", newArrayList("6", "7"));
        AreaCodes areaCodes = testClass.buildAreaCodes();

        // THEN
        Map<State, WeightedList<String>> expectedAreaCodesByState = new HashMap<>();
        expectedAreaCodesByState.put(OREGON, new WeightedListBuilderByVolume<String>(scale)
                .addItem("1", 1)
                .addItem("2", 2)
                .addItem("3", 3)
                .addItem("4", 1)
                .build());
        expectedAreaCodesByState.put(WASHINGTON, new WeightedListBuilderByVolume<String>(scale)
                .addItem("6", 2)
                .addItem("7", 1)
                .build());

        ArrayListMultimap<String, String> expectedAreaCodesByZipCode = ArrayListMultimap.create();
        expectedAreaCodesByZipCode.putAll("97201", newArrayList("1", "2", "3"));
        expectedAreaCodesByZipCode.putAll("97202", newArrayList("2", "3"));
        expectedAreaCodesByZipCode.putAll("97203", newArrayList("4", "3"));
        expectedAreaCodesByZipCode.putAll("98601", newArrayList("6"));
        expectedAreaCodesByZipCode.putAll("98602", newArrayList("6", "7"));

        AreaCodes expectedAreaCodes = new AreaCodes(expectedAreaCodesByZipCode, expectedAreaCodesByState);
        assertEquals(expectedAreaCodes, areaCodes);
    }

}