package com.gnopai.contactgen.statistics.name;

import com.gnopai.contactgen.model.Gender;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.MockFileProcessor;
import org.junit.Test;

import static java.util.Arrays.asList;
import static com.gnopai.contactgen.model.Gender.FEMALE;
import static com.gnopai.contactgen.model.Gender.MALE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FirstNamesStatisticsLoaderTest {

    @Test
    public void testLoadNamesFromFile() throws Exception {
        // GIVEN
        MockFileProcessor fileProcessor = new MockFileProcessor(
                "2010 Noah 18090 Sophia 21075",
                "1990 Michael 462265 Jessica 303053",
                "1990 Jacob 298219 Samantha 223979",
                "1980 Michael 663445 Jessica 469415",
                "1980 Christopher 554725 Jennifer 440818",
                "1980 Matthew 458831 Amanda 369679"
        );
        FirstNamesStatisticsLoader testClass = new FirstNamesStatisticsLoader(10, fileProcessor);

        // WHEN
        ContactStatistics contactStatistics = testClass.loadStatistics(ContactStatistics.empty());

        // THEN
        FirstNames firstNames = contactStatistics.getFirstNames();
        assertNames(firstNames, MALE, 2010, "Noah");
        assertNames(firstNames, FEMALE, 2010, "Sophia");
        assertNames(firstNames, MALE, 1990, "Michael", "Jacob");
        assertNames(firstNames, FEMALE, 1990, "Jessica", "Samantha");
        assertNames(firstNames, MALE, 1980, "Michael", "Christopher", "Matthew");
        assertNames(firstNames, FEMALE, 1980, "Jessica", "Jennifer", "Amanda");
        assertNull(firstNames.getFirstNameList(MALE, 2000));
    }

    private void assertNames(FirstNames firstNames, Gender gender, int decade, String... expectedNames) {
        assertEquals(asList(expectedNames), firstNames.getFirstNameList(gender, decade).toList());
    }
}
