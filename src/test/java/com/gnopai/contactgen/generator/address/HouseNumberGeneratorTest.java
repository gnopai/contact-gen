package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.generator.address.HouseNumberGenerator.MAX_LENGTH;
import static com.gnopai.contactgen.generator.address.HouseNumberGenerator.MIN_LENGTH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HouseNumberGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        when(randomGenerator.selectInteger(MIN_LENGTH, MAX_LENGTH)).thenReturn(3);
        when(randomGenerator.selectInteger(100, 1000)).thenReturn(666);

        HouseNumberGenerator testClass = new HouseNumberGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), ContactData.empty());

        // THEN
        assertEquals("666", result.getHouseNumber());
    }

}