package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StreetNameGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        List<String> streetNames = List.of("First", "Second", "Third");
        when(randomGenerator.selectUniformlyDistributedListItem(streetNames)).thenReturn("Awesome");
        ContactStatistics contactStatistics = ContactStatistics.builder().streetNames(streetNames).build();

        StreetNameGenerator testClass = new StreetNameGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals("Awesome", result.getStreetName());
    }

}