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
public class PhoneExchangeGeneratorTest {
    @Mock RandomGenerator randomGenerator;

    @Test
    public void test() throws Exception {
        // GIVEN
        when(randomGenerator.selectInteger(2, 10)).thenReturn(3);
        when(randomGenerator.selectInteger(0, 10)).thenReturn(4).thenReturn(5);

        PhoneExchangeGenerator testClass = new PhoneExchangeGenerator(randomGenerator);

        // WHEN
        ContactData contactData = testClass.generate(ContactStatistics.empty(), ContactData.empty());

        // THEN
        assertEquals("345", contactData.getPhoneExchange());
    }

}