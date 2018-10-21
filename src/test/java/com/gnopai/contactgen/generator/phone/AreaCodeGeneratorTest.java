package com.gnopai.contactgen.generator.phone;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.phone.AreaCodes;
import com.gnopai.contactgen.statistics.util.WeightedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static com.gnopai.contactgen.generator.phone.AreaCodeGenerator.CHANCE_OF_LOCAL_AREA_CODE;
import static com.gnopai.contactgen.generator.phone.AreaCodeGenerator.CHANCE_OF_STATE_VS_REMOTE_AREA_CODE;
import static com.gnopai.contactgen.model.State.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AreaCodeGeneratorTest {
    @Mock RandomGenerator randomGenerator;
    @Mock AreaCodes areaCodes;

    @Test
    public void testLocalAreaCode() throws Exception {
        // GIVEN
        when(randomGenerator.selectChance(CHANCE_OF_LOCAL_AREA_CODE)).thenReturn(true);
        when(randomGenerator.selectChance(CHANCE_OF_STATE_VS_REMOTE_AREA_CODE)).thenReturn(true);
        String zipCode = "97201";
        ContactData contactData = ContactData.builder()
                .zipCode(zipCode)
                .state(OREGON)
                .build();
        List<String> areaCodesForZipCode = List.of("503", "971");
        when(areaCodes.getAreaCodesForZipCode(zipCode)).thenReturn(areaCodesForZipCode);
        String expectedAreaCode = "666";
        when(randomGenerator.selectUniformlyDistributedListItem(areaCodesForZipCode)).thenReturn(expectedAreaCode);

        ContactStatistics contactStatistics = ContactStatistics.builder().areaCodes(areaCodes).build();

        AreaCodeGenerator testClass = new AreaCodeGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertEquals(expectedAreaCode, result.getPhoneAreaCode());
    }

    @Test
    public void testInStateAreaCode() throws Exception {
        // GIVEN
        when(randomGenerator.selectChance(CHANCE_OF_LOCAL_AREA_CODE)).thenReturn(false);
        when(randomGenerator.selectChance(CHANCE_OF_STATE_VS_REMOTE_AREA_CODE)).thenReturn(true);
        State state = OREGON;
        ContactData contactData = ContactData.builder()
                .zipCode("97201")
                .state(state)
                .build();
        String expectedAreaCode = "666";
        WeightedList<String> areaCodesForState = new WeightedList<>();
        areaCodesForState.add(expectedAreaCode, new BigDecimal("1.0"));
        when(areaCodes.getAreaCodesForState(state)).thenReturn(areaCodesForState);
        when(randomGenerator.selectWeightDistributedListItem(areaCodesForState)).thenReturn(expectedAreaCode);

        ContactStatistics contactStatistics = ContactStatistics.builder().areaCodes(areaCodes).build();

        AreaCodeGenerator testClass = new AreaCodeGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertEquals(expectedAreaCode, result.getPhoneAreaCode());
    }

    @Test
    public void testOutOfStateAreaCode() throws Exception {
        // GIVEN
        when(randomGenerator.selectChance(CHANCE_OF_LOCAL_AREA_CODE)).thenReturn(false);
        when(randomGenerator.selectChance(CHANCE_OF_STATE_VS_REMOTE_AREA_CODE)).thenReturn(false);
        ContactData contactData = ContactData.builder()
                .zipCode("97201")
                .state(OREGON)
                .build();
        String expectedAreaCode = "666";

        State otherState = ILLINOIS;
        WeightedList<State> allStates = new WeightedList<>();
        allStates.add(ALABAMA, new BigDecimal("5.4"));
        when(randomGenerator.selectWeightDistributedListItem(allStates)).thenReturn(otherState);

        WeightedList<String> areaCodesForState = new WeightedList<>();
        areaCodesForState.add(expectedAreaCode, new BigDecimal("1.0"));
        when(areaCodes.getAreaCodesForState(otherState)).thenReturn(areaCodesForState);
        when(randomGenerator.selectWeightDistributedListItem(areaCodesForState)).thenReturn(expectedAreaCode);

        ContactStatistics contactStatistics = ContactStatistics.builder()
                .areaCodes(areaCodes)
                .states(allStates)
                .build();

        AreaCodeGenerator testClass = new AreaCodeGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, contactData);

        // THEN
        assertEquals(expectedAreaCode, result.getPhoneAreaCode());
    }

}