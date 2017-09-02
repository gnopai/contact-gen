package com.gnopai.contactgen.generator.name;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.name.FirstNames;
import com.gnopai.contactgen.statistics.util.WeightedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static com.gnopai.contactgen.model.Gender.FEMALE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FirstNameGeneratorTest {
    @Mock RandomGenerator randomGenerator;
    @Mock FirstNames firstNames;
    @Mock WeightedList<String> firstNameList;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        ContactData contact = ContactData.builder()
                .gender(FEMALE)
                .dateOfBirth(LocalDate.of(1994, 4, 4))
                .build();

        ContactStatistics contactStatistics = ContactStatistics.builder().firstNames(firstNames).build();
        when(firstNames.getFirstNameList(FEMALE, 1990)).thenReturn(firstNameList);
        when(randomGenerator.selectWeightDistributedListItem(firstNameList)).thenReturn("Bobette");

        FirstNameGenerator testClass = new FirstNameGenerator(randomGenerator);

        // WHEN
        ContactData contactData = testClass.generate(contactStatistics, contact);

        // THEN
        assertEquals("Bobette", contactData.getFirstName());
    }

    @Test
    public void testShouldRun() throws Exception {
        // GIVEN
        ContactData contactData = ContactData.empty();
        FirstNameGenerator testClass = new FirstNameGenerator(randomGenerator);

        // WHEN / THEN
        assertTrue(testClass.requiresMoreData(contactData));
        assertTrue(testClass.requiresMoreData(contactData.withGender(FEMALE)));
        assertTrue(testClass.requiresMoreData(contactData.withDateOfBirth(LocalDate.MAX)));
        assertFalse(testClass.requiresMoreData(contactData.withDateOfBirth(LocalDate.MAX).withGender(FEMALE)));
    }

}