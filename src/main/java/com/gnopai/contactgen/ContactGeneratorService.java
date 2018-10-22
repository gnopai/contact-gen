package com.gnopai.contactgen;

import com.gnopai.contactgen.generator.ContactGenerator;
import com.gnopai.contactgen.model.Contact;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.ContactStatisticsLoader;
import com.google.inject.Inject;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ContactGeneratorService {
    private final ContactGenerator contactGenerator;
    private final ContactStatisticsLoader contactStatisticsLoader;

    @Inject
    public ContactGeneratorService(ContactGenerator contactGenerator, ContactStatisticsLoader contactStatisticsLoader) {
        this.contactGenerator = contactGenerator;
        this.contactStatisticsLoader = contactStatisticsLoader;
    }

    public List<Contact> generateContacts(int count) {
        ContactStatistics contactStatistics = loadContactStatistics();
        return IntStream.range(0, count)
                .mapToObj(i -> contactGenerator.generateContact(contactStatistics))
                .collect(toUnmodifiableList());
    }

    private ContactStatistics loadContactStatistics() {
        try {
            return contactStatisticsLoader.loadStatistics(ContactStatistics.empty());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load statistics", e);
        }
    }
}
