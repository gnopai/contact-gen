package com.gnopai.contactgen.generator.gender;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.generator.gender.GenderGenerator.CHANCE_OF_BEING_FEMALE;
import static com.gnopai.contactgen.model.Gender.FEMALE;
import static com.gnopai.contactgen.model.Gender.MALE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenderGeneratorTest {
    @Mock private RandomGenerator randomGenerator;
    @Mock private ContactStatistics contactStatistics;

    @Test
    public void testGenerateGender_female() throws Exception {
        // GIVEN
        when(randomGenerator.selectChance(CHANCE_OF_BEING_FEMALE)).thenReturn(true);

        GenderGenerator testClass = new GenderGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals(FEMALE, result.getGender());
    }

    @Test
    public void testGenerateGender_male() throws Exception {
        // GIVEN
        when(randomGenerator.selectChance(CHANCE_OF_BEING_FEMALE)).thenReturn(false);

        GenderGenerator testClass = new GenderGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals(MALE, result.getGender());
    }

}
