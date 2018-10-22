package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.statistics.ContactStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactGeneratorTest {
    @Mock ContactTransformer contactTransformer;
    @Mock FieldGenerator fieldGenerator1;
    @Mock FieldGenerator fieldGenerator2;
    @Mock FieldGenerator fieldGenerator3;
    @Mock FieldGenerators fieldGenerators;

    @Test
    public void testGenerateContact() {
        // GIVEN
        ContactData initialContactData = ContactData.empty();
        ContactStatistics contactStatistics = ContactStatistics.empty();

        ContactData contactDataAfterGenerator1 = initialContactData.withPhoneAreaCode("555");
        ContactData contactDataAfterGenerator2 = contactDataAfterGenerator1.withPhoneExchange("666");
        ContactData contactDataAfterGenerator3 = contactDataAfterGenerator2.withPhoneLine("666");

        when(fieldGenerator1.generate(contactStatistics, initialContactData)).thenReturn(contactDataAfterGenerator1);
        when(fieldGenerator2.generate(contactStatistics, contactDataAfterGenerator1)).thenReturn(contactDataAfterGenerator2);
        when(fieldGenerator3.generate(contactStatistics, contactDataAfterGenerator2)).thenReturn(contactDataAfterGenerator3);

        when(fieldGenerator1.requiresMoreData(any(ContactData.class))).thenReturn(false);
        when(fieldGenerator2.requiresMoreData(any(ContactData.class))).thenReturn(false);
        when(fieldGenerator3.requiresMoreData(any(ContactData.class))).thenReturn(false);

        when(fieldGenerators.get()).thenReturn(List.of(fieldGenerator1, fieldGenerator2, fieldGenerator3));

        Contact expectedContact = Contact.builder().ssn("yay").build();
        when(contactTransformer.transform(contactDataAfterGenerator3)).thenReturn(expectedContact);

        ContactGenerator testClass = new ContactGenerator(fieldGenerators, contactTransformer);

        // WHEN
        Contact contact = testClass.generateContact(contactStatistics);

        // THEN
        assertEquals(expectedContact, contact);
    }

    @Test(expected = IllegalStateException.class)
    public void testMissingData() {
        // GIVEN
        when(fieldGenerator1.requiresMoreData(any(ContactData.class))).thenReturn(false);
        when(fieldGenerator2.requiresMoreData(any(ContactData.class))).thenReturn(false);
        when(fieldGenerator3.requiresMoreData(any(ContactData.class))).thenReturn(true);
        when(fieldGenerators.get()).thenReturn(List.of(fieldGenerator1, fieldGenerator2, fieldGenerator3));

        ContactGenerator testClass = new ContactGenerator(fieldGenerators, contactTransformer);

        // WHEN
        testClass.generateContact(ContactStatistics.empty());

        // THEN exception is thrown
    }

}