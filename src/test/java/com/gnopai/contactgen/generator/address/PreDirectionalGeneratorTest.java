package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.statistics.address.Directional.SOUTHEAST;
import static com.gnopai.contactgen.statistics.address.Directional.SOUTHWEST;
import static com.gnopai.contactgen.generator.address.PreDirectionalGenerator.CHANCE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreDirectionalGeneratorTest {
    @Mock DirectionalGenerator directionalGenerator;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        when(directionalGenerator.generate(CHANCE)).thenReturn(SOUTHEAST);
        PreDirectionalGenerator testClass = new PreDirectionalGenerator(directionalGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), contactData);

        // THEN
        assertEquals(SOUTHEAST, result.getPreDirectional());
    }

    @Test
    public void testGenerate_postDirectionalPresent() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.builder().postDirectional(SOUTHWEST).build();
        when(directionalGenerator.generate(CHANCE)).thenReturn(SOUTHEAST);
        PreDirectionalGenerator testClass = new PreDirectionalGenerator(directionalGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), contactData);

        // THEN
        assertNull(result.getPreDirectional());
    }
}