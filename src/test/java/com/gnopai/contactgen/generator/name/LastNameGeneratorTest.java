package com.gnopai.contactgen.generator.name;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.WeightedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LastNameGeneratorTest {
    @Mock RandomGenerator randomGenerator;
    @Mock WeightedList<String> lastNames;

    @Test
    public void testGenerate() throws Exception {
        ContactStatistics contactStatistics = ContactStatistics.builder().lastNames(lastNames).build();
        when(randomGenerator.selectWeightDistributedListItem(lastNames)).thenReturn("Bobson");

        LastNameGenerator testClass = new LastNameGenerator(randomGenerator);

        // WHEN
        ContactData contactData = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals("Bobson", contactData.getLastName());
    }
}