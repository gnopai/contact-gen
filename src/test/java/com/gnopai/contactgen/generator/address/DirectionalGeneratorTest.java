package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.random.Odds;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.Directional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.statistics.address.Directional.NORTHEAST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectionalGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        Odds odds = Odds.of(1).in(5);
        when(randomGenerator.selectChance(odds))
                .thenReturn(false) // first run not present
                .thenReturn(true); // second run present

        Directional expectedDirectional = NORTHEAST;
        when(randomGenerator.selectUniformlyDistributedListItem(Directional.valuesAsList())).thenReturn(expectedDirectional);

        DirectionalGenerator testClass = new DirectionalGenerator(randomGenerator);

        // WHEN / THEN
        assertNull(testClass.generate(odds));
        assertEquals(expectedDirectional, testClass.generate(odds));
    }


}