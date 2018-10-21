package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.address.ZipCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.gnopai.contactgen.model.State.OREGON;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZipCodeGeneratorTest {
    @Mock RandomGenerator randomGenerator;
    @Mock ZipCodes zipCodes;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        String city = "Portland";
        State state = OREGON;
        List<String> zipCodesForCity = List.of("97201", "97202", "97203");
        when(zipCodes.getZipCodes(city, state)).thenReturn(zipCodesForCity);
        when(randomGenerator.selectUniformlyDistributedListItem(zipCodesForCity)).thenReturn("97204");
        ContactData contactData = ContactData.builder().city(city).state(state).build();
        ContactStatistics contactStatistics = ContactStatistics.builder().zipCodes(zipCodes).build();

        ZipCodeGenerator testClass = new ZipCodeGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertEquals("97204", result.getZipCode());
    }

    @Test
    public void testRequiresMoreData() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        ZipCodeGenerator testClass = new ZipCodeGenerator(randomGenerator);

        // WHEN / THEN
        assertTrue(testClass.requiresMoreData(contactData));
        assertTrue(testClass.requiresMoreData(contactData.withCity("Portland")));
        assertTrue(testClass.requiresMoreData(contactData.withState(OREGON)));
        assertFalse(testClass.requiresMoreData(contactData.withCity("Portland").withState(OREGON)));
    }

}