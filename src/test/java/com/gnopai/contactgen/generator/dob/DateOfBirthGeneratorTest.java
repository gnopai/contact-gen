package com.gnopai.contactgen.generator.dob;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DateOfBirthGeneratorTest {
    @Mock private RandomGenerator randomGenerator;
    @Mock private WeightedList<Range> ageRanges;

    @Test
    public void testGenerateDateOfBirth() throws Exception {
        // WHEN
        Range ageRange = new Range(17, 21);
        when(randomGenerator.selectWeightDistributedListItem(ageRanges)).thenReturn(ageRange);
        int expectedAge = 19;
        when(randomGenerator.selectInteger(ageRange)).thenReturn(expectedAge);

        int monthOfBirth = 3;
        when(randomGenerator.selectInteger(1, 13)).thenReturn(monthOfBirth);
        int dayOfMonthOfBirth = 14;
        when(randomGenerator.selectInteger(1, 32)).thenReturn(dayOfMonthOfBirth);

        ContactStatistics contactStatistics = ContactStatistics.builder().ageRanges(ageRanges).build();

        DateOfBirthGenerator testClass = new DateOfBirthGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        int yearOfBirth = LocalDate.now().minus(expectedAge, YEARS).getYear();
        LocalDate expectedDateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfMonthOfBirth);
        assertEquals(expectedDateOfBirth, result.getDateOfBirth());
    }

}
