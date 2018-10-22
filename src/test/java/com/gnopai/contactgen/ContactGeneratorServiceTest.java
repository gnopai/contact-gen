package com.gnopai.contactgen;

import com.gnopai.contactgen.generator.ContactGenerator;
import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.ContactStatisticsLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactGeneratorServiceTest {
    @Mock ContactGenerator contactGenerator;
    @Mock ContactStatisticsLoader contactStatisticsLoader;

    @Test
    public void testGenerateContacts() throws Exception {
        // GIVEN
        ContactStatistics contactStatistics = ContactStatistics.builder().build();
        when(contactStatisticsLoader.loadStatistics(ContactStatistics.empty())).thenReturn(contactStatistics);

        Contact contact1 = Contact.builder().ssn("1").build();
        Contact contact2 = Contact.builder().ssn("2").build();
        Contact contact3 = Contact.builder().ssn("3").build();
        Contact contact4 = Contact.builder().ssn("4").build();
        when(contactGenerator.generateContact(contactStatistics))
                .thenReturn(contact1)
                .thenReturn(contact2)
                .thenReturn(contact3)
                .thenReturn(contact4);

        ContactGeneratorService testClass = new ContactGeneratorService(contactGenerator, contactStatisticsLoader);

        // WHEN
        List<Contact> contacts = testClass.generateContacts(3);

        // THEN
        List<Contact> expectedContacts = List.of(contact1, contact2, contact3);
        assertEquals(expectedContacts, contacts);
    }
}