package com.gnopai.contactgen.statistics.name;

import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.MockFileProcessor;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class LastNamesStatisticsLoaderTest {
    @Test
    public void testLoadAgeRangesFromFile() throws Exception {
        // GIVEN
        MockFileProcessor fileProcessor = new MockFileProcessor(
                "name,rank,count,prop100k,cum_prop100k,pctwhite,pctblack,pctapi,pctaian,pct2prace,pcthispanic",
                "SMITH,1,2376206,880.85,880.85,73.35,22.22,0.40,0.85,1.63,1.56",
                "JOHNSON,2,1857160,688.44,1569.30,61.55,33.80,0.42,0.91,1.82,1.50",
                "WILLIAMS,3,1534042,568.66,2137.96,48.52,46.72,0.37,0.78,2.01,1.60",
                "BROWN,4,1380145,511.62,2649.58,60.71,34.54,0.41,0.83,1.86,1.64",
                "JONES,5,1362755,505.17,3154.75,57.69,37.73,0.35,0.94,1.85,1.44"
        );
        LastNamesStatisticsLoader testClass = new LastNamesStatisticsLoader(10, fileProcessor);

        // WHEN
        ContactStatistics contactStatistics = testClass.loadStatistics(ContactStatistics.empty());

        // THEN
        List<String> expected = newArrayList("Smith", "Johnson", "Williams", "Brown", "Jones");
        assertEquals(expected, contactStatistics.getLastNames().toList());
    }
}
