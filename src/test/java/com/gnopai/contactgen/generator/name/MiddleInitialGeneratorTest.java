package com.gnopai.contactgen.generator.name;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.generator.name.MiddleInitialGenerator.ODDS_OF_HAVING_MIDDLE_INITIAL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiddleInitialGeneratorTest {
    @Mock RandomGenerator randomGenerator;
    @Mock FirstNameGenerator firstNameGenerator;

    @Test
    public void testGenerate_middleInitial() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        ContactStatistics contactStatistics = ContactStatistics.builder().build();
        when(randomGenerator.selectChance(ODDS_OF_HAVING_MIDDLE_INITIAL)).thenReturn(true);
        when(firstNameGenerator.generateFirstName(contactStatistics, contactData)).thenReturn("Bob");

        MiddleInitialGenerator testClass = new MiddleInitialGenerator(randomGenerator, firstNameGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertEquals("B", result.getMiddleInitial());
    }

    @Test
    public void testGenerate_noMiddleInitial() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        ContactStatistics contactStatistics = ContactStatistics.builder().build();
        when(randomGenerator.selectChance(ODDS_OF_HAVING_MIDDLE_INITIAL)).thenReturn(false);
        when(firstNameGenerator.generateFirstName(contactStatistics, contactData)).thenReturn("Bob");

        MiddleInitialGenerator testClass = new MiddleInitialGenerator(randomGenerator, firstNameGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertNull(result.getMiddleInitial());
    }

    @Test
    public void testShouldRun() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        when(firstNameGenerator.requiresMoreData(contactData))
                .thenReturn(false)
                .thenReturn(true);

        MiddleInitialGenerator testClass = new MiddleInitialGenerator(randomGenerator, firstNameGenerator);

        // WHEN / THEN
        assertFalse(testClass.requiresMoreData(contactData));
        assertTrue(testClass.requiresMoreData(contactData));
    }
}