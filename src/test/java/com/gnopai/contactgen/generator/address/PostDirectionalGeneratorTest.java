package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.statistics.address.Directional.SOUTHEAST;
import static com.gnopai.contactgen.statistics.address.Directional.SOUTHWEST;
import static com.gnopai.contactgen.generator.address.PostDirectionalGenerator.CHANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostDirectionalGeneratorTest {
    @Mock DirectionalGenerator directionalGenerator;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        when(directionalGenerator.generate(CHANCE)).thenReturn(SOUTHEAST);
        PostDirectionalGenerator testClass = new PostDirectionalGenerator(directionalGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), contactData);

        // THEN
        assertEquals(SOUTHEAST, result.getPostDirectional());
    }

    @Test
    public void testGenerate_preDirectionalPresent() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.builder().preDirectional(SOUTHWEST).build();
        when(directionalGenerator.generate(CHANCE)).thenReturn(SOUTHEAST);
        PostDirectionalGenerator testClass = new PostDirectionalGenerator(directionalGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), contactData);

        // THEN
        assertNull(result.getPostDirectional());
    }
}