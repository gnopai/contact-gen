package com.gnopai.contactgen.statistics.dob;

import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.MockFileProcessor;
import com.gnopai.contactgen.statistics.util.Range;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DateOfBirthStatisticsLoaderTest {

    @Test
    public void testLoadAgeRangesFromFile() throws Exception {
        // GIVEN
        MockFileProcessor fileProcessor = new MockFileProcessor(
                "0-25 10",
                "25-50 10",
                "50-75 10",
                "75-100 10"
        );
        DateOfBirthStatisticsLoader testClass = new DateOfBirthStatisticsLoader(10, fileProcessor);

        // WHEN
        ContactStatistics contactStatistics = testClass.loadStatistics(ContactStatistics.empty());

        // THEN
        List<Range> expected = List.of(
                new Range(0, 25),
                new Range(25, 50),
                new Range(50, 75),
                new Range(75, 100));
        assertEquals(expected, contactStatistics.getAgeRanges().toList());
    }
}
