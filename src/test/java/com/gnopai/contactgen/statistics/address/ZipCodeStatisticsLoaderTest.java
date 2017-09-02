package com.gnopai.contactgen.statistics.address;

import au.com.bytecode.opencsv.CSVParser;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.phone.AreaCodeCounter;
import com.gnopai.contactgen.statistics.phone.AreaCodes;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.gnopai.contactgen.statistics.util.MockFileProcessor;
import com.google.common.collect.ArrayListMultimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Set;

import static com.gnopai.contactgen.model.State.*;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZipCodeStatisticsLoaderTest {
    @Mock AreaCodeCounter areaCodeCounter;

    @Test
    public void testLoadZipCodeDataFromFile() throws Exception {
        // GIVEN
        FileProcessor fileProcessor = new MockFileProcessor(
                "\"zip\",\"type\",\"primary_city\",\"acceptable_cities\",\"unacceptable_cities\",\"state\",\"county\",\"timezone\",\"area_codes\",\"latitude\",\"longitude\",\"world_region\",\"country\",\"decommissioned\",\"estimated_population\",\"notes\"",
                "\"99790\",\"STANDARD\",\"Fairbanks\",,,\"AK\",\"Fairbanks North Star Borough\",\"America/Anchorage\",\"907\",\"64.51\",\"-147.66\",\"NA\",\"US\",\"0\",\"0\",",
                "\"99706\",\"PO BOX\",\"Fairbanks\",,,\"AK\",\"Fairbanks North Star Borough\",\"America/Anchorage\",\"907\",\"64.83\",\"-147.88\",\"NA\",\"US\",\"0\",\"1357\",",
                "\"99707\",\"PO BOX\",\"Fairbanks\",,,\"AK\",\"Fairbanks North Star Borough\",\"America/Anchorage\",\"907\",\"64.83\",\"-147.69\",\"NA\",\"US\",\"0\",\"4743\",",
                "\"09328\",\"MILITARY\",\"Apo\",,,\"AE\",,,,\"0\",\"0\",\"AS\",\"OM\",\"0\",\"0\",\";Country and installation from  MPSA Ballot\"",
                "\"98275\",\"STANDARD\",\"Mukilteo\",,,\"WA\",\"Snohomish County\",\"America/Los_Angeles\",\"425,206\",\"47.91\",\"-122.3\",\"NA\",\"US\",\"0\",\"18276\",",
                "\"97420\",\"STANDARD\",\"Coos Bay\",\"Charleston\",\"Coos Head Naval Facility, Eastside\",\"OR\",\"Coos County\",\"America/Los_Angeles\",\"541\",\"43.37\",\"-124.14\",\"NA\",\"US\",\"0\",\"19802\","
        );
        AreaCodes expectedAreaCodes = new AreaCodes(ArrayListMultimap.create(), new HashMap<>());
        when(areaCodeCounter.buildAreaCodes()).thenReturn(expectedAreaCodes);
        ZipCodeStatisticsLoader testClass = new ZipCodeStatisticsLoader(fileProcessor, new CSVParser(), () -> areaCodeCounter);

        // WHEN
        ContactStatistics contactStatistics = testClass.loadStatistics(ContactStatistics.empty());

        // THEN
        ZipCodes zipCodes = contactStatistics.getZipCodes();
        assertZipCodesMatch(zipCodes, "fairbanks", ALASKA, "99790", "99706", "99707");
        assertZipCodesMatch(zipCodes, "mukilteo", WASHINGTON, "98275");
        assertZipCodesMatch(zipCodes, "Coos Bay", OREGON, "97420");

        assertEquals(expectedAreaCodes, contactStatistics.getAreaCodes());
        verify(areaCodeCounter).addAll(ALASKA, "99790", newArrayList("907"));
        verify(areaCodeCounter).addAll(ALASKA, "99706", newArrayList("907"));
        verify(areaCodeCounter).addAll(ALASKA, "99707", newArrayList("907"));
        verify(areaCodeCounter).addAll(WASHINGTON, "98275", newArrayList("425", "206"));
        verify(areaCodeCounter).addAll(OREGON, "97420", newArrayList("541"));
        verify(areaCodeCounter).buildAreaCodes();
        verifyNoMoreInteractions(areaCodeCounter);
    }

    private void assertZipCodesMatch(ZipCodes zipCodes, String city, State state, String... expectedZipCodes) {
        Set<String> actualZipCodes = newHashSet(zipCodes.getZipCodes(city, state));
        assertEquals(newHashSet(expectedZipCodes), actualZipCodes);
    }
}
