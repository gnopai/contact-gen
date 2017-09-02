package com.gnopai.contactgen.statistics.address;

import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.MockFileProcessor;
import org.junit.Test;

import java.util.List;

import static com.gnopai.contactgen.model.State.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class StateStatisticsLoaderTest {

    @Test
    public void testLoadStatesFromFile() throws Exception {
        // GIVEN
        MockFileProcessor fileProcessor = new MockFileProcessor(
                "WI 15",
                "MN 55",
                "CO 2",
                "AL 14",
                "SC 27"
        );
        StateStatisticsLoader testClass = new StateStatisticsLoader(10, fileProcessor);

        // WHEN
        ContactStatistics contactStatistics = testClass.loadStatistics(ContactStatistics.empty());

        // THEN
        List<State> expectedStates = newArrayList(WISCONSIN, MINNESOTA, COLORADO, ALABAMA, SOUTH_CAROLINA);
        assertEquals(expectedStates, contactStatistics.getStates().toList());
    }
}
