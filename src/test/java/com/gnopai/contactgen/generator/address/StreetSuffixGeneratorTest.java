package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.StreetSuffix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.statistics.address.StreetSuffix.DRIVE;
import static com.gnopai.contactgen.generator.address.StreetSuffixGenerator.ODDS_OF_LONG_STREET_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StreetSuffixGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void testGenerate_shortName() throws Exception {
        // GIVEN
        StreetSuffix streetSuffix = DRIVE;
        when(randomGenerator.selectNormallyDistributedListItem(StreetSuffix.valuesAsList())).thenReturn(streetSuffix);
        when(randomGenerator.selectChance(ODDS_OF_LONG_STREET_NAME)).thenReturn(false);
        ContactStatistics contactStatistics = ContactStatistics.builder().build();

        StreetSuffixGenerator testClass = new StreetSuffixGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals(streetSuffix.getShortName(), result.getStreetSuffix());
    }

    @Test
    public void testGenerate_longName() throws Exception {
        // GIVEN
        StreetSuffix streetSuffix = DRIVE;
        when(randomGenerator.selectNormallyDistributedListItem(StreetSuffix.valuesAsList())).thenReturn(streetSuffix);
        when(randomGenerator.selectChance(ODDS_OF_LONG_STREET_NAME)).thenReturn(true);
        ContactStatistics contactStatistics = ContactStatistics.builder().build();

        StreetSuffixGenerator testClass = new StreetSuffixGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals(streetSuffix.getLongName(), result.getStreetSuffix());
    }
}