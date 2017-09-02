package com.gnopai.contactgen.generator;

import com.gnopai.contactgen.statistics.ContactStatistics;

public interface FieldGenerator {

    ContactData generate(ContactStatistics contactStatistics, ContactData contactData);

    default boolean requiresMoreData(ContactData contactData) {
        return false;
    }

}
