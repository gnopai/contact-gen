package com.gnopai.contactgen.statistics.address;

import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.gnopai.contactgen.statistics.util.MockFileProcessor;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.gnopai.contactgen.model.State.*;
import static org.junit.Assert.assertEquals;

public class CityStatisticsLoaderTest {

    @Test
    public void testLoadCityDataFromFile() throws Exception {
        // GIVEN
        FileProcessor fileProcessor = new MockFileProcessor(
                "KS, Wichita, Overland Park, Kansas City, Topeka",
                "NC, Charlotte, Raleigh, Greensboro",
                "PR, San Juan, Bayamon"
        );

        CityStatisticsLoader testClass = new CityStatisticsLoader(fileProcessor);

        // WHEN
        ContactStatistics contactStatistics = testClass.loadStatistics(ContactStatistics.empty());

        // THEN
        Map<State, List<String>> citiesByState = contactStatistics.getCitiesByState();
        assertEquals(3, citiesByState.size());
        assertEquals(newArrayList("Wichita", "Overland Park", "Kansas City", "Topeka"), citiesByState.get(KANSAS));
        assertEquals(newArrayList("Charlotte", "Raleigh", "Greensboro"), citiesByState.get(NORTH_CAROLINA));
        assertEquals(newArrayList("San Juan", "Bayamon"), citiesByState.get(PUERTO_RICO));
    }
}
