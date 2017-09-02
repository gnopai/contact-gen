package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.address.UnitType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.generator.address.UnitGenerator.ODDS;
import static com.gnopai.contactgen.generator.address.UnitGenerator.UNIT_NUMBER_MAX;
import static com.gnopai.contactgen.generator.address.UnitGenerator.UNIT_NUMBER_MIN;
import static com.gnopai.contactgen.statistics.address.UnitType.APARTMENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnitGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void testGenerate_unitPresent() throws Exception {
        // GIVEN
        UnitType unitType = APARTMENT;
        when(randomGenerator.selectChance(ODDS)).thenReturn(true);
        when(randomGenerator.selectNormallyDistributedListItem(UnitType.valuesAsList())).thenReturn(unitType);
        when(randomGenerator.selectInteger(UNIT_NUMBER_MIN, UNIT_NUMBER_MAX)).thenReturn(666);

        UnitGenerator testClass = new UnitGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), ContactData.empty());

        // THEN
        assertEquals(new Unit("666", unitType), result.getUnit());
    }

    @Test
    public void testGenerate_unitNotPresent() throws Exception {
        // GIVEN
        UnitType unitType = APARTMENT;
        when(randomGenerator.selectChance(ODDS)).thenReturn(false);
        when(randomGenerator.selectNormallyDistributedListItem(UnitType.valuesAsList())).thenReturn(unitType);
        when(randomGenerator.selectInteger(UNIT_NUMBER_MIN, UNIT_NUMBER_MAX)).thenReturn(666);

        UnitGenerator testClass = new UnitGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(ContactStatistics.builder().build(), ContactData.empty());

        // THEN
        assertNull(result.getUnit());
    }

}