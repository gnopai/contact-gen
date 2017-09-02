package com.gnopai.contactgen.generator.phone;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneLineGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void test() throws Exception {
        // GIVEN
        when(randomGenerator.selectInteger(0, 10000)).thenReturn(345);
        PhoneLineGenerator testClass = new PhoneLineGenerator(randomGenerator);

        // WHEN
        ContactData contactData = testClass.generate(ContactStatistics.empty(), ContactData.empty());

        // THEN
        assertEquals("0345", contactData.getPhoneLine());

    }
}