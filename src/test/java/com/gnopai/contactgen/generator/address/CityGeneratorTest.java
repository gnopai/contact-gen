package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static com.gnopai.contactgen.model.State.OREGON;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        State state = OREGON;
        List<String> cities = newArrayList("Beavertron", "Portland", "Gresham");
        Map<State, List<String>> citiesByState = ImmutableMap.of(state, cities);
        when(randomGenerator.selectNormallyDistributedListItem(cities)).thenReturn("Salem");

        ContactStatistics contactStatistics = ContactStatistics.builder().citiesByState(citiesByState).build();
        ContactData contactData = ContactData.builder().state(state).build();

        CityGenerator testClass = new CityGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertEquals("Salem", result.getCity());
    }

    @Test
    public void testRequiresMoreData() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        CityGenerator testClass = new CityGenerator(randomGenerator);

        // WHEN / THEN
        assertTrue(testClass.requiresMoreData(contactData));
        assertFalse(testClass.requiresMoreData(contactData.withState(OREGON)));
    }
}