package com.gnopai.contactgen.statistics;

import com.gnopai.contactgen.statistics.address.ZipCodes;
import com.gnopai.contactgen.statistics.name.FirstNames;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactStatisticsLoaderTest {
    @Mock StatisticsLoader firstLoader;
    @Mock StatisticsLoader secondLoader;

    @Test
    public void testLoadStatistics() throws Exception {
        // GIVEN
        ContactStatistics contactStatistics = ContactStatistics.builder().build();

        FirstNames firstNames = mock(FirstNames.class);
        ContactStatistics contactStatisticsAfterFirstLoader = contactStatistics.withFirstNames(firstNames);
        when(firstLoader.loadStatistics(contactStatistics)).thenReturn(contactStatisticsAfterFirstLoader);

        ZipCodes zipCodes = mock(ZipCodes.class);
        ContactStatistics contactStatisticsAfterSecondLoader = contactStatisticsAfterFirstLoader.withZipCodes(zipCodes);
        when(secondLoader.loadStatistics(contactStatisticsAfterFirstLoader)).thenReturn(contactStatisticsAfterSecondLoader);

        ContactStatisticsLoader testClass = new ContactStatisticsLoader(newArrayList(firstLoader, secondLoader));

        // WHEN
        ContactStatistics result = testClass.loadStatistics(contactStatistics);

        // THEN
        assertEquals(contactStatisticsAfterSecondLoader, result);
    }

}