package com.gnopai.contactgen.statistics.address;

import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.FlatListDataLoader;
import com.google.inject.Inject;

import java.util.List;

public class StreetNamesStatisticsLoader implements StatisticsLoader {
    private static final String STREET_NAMES_FILE = "/street-names.txt";
    private final FlatListDataLoader flatListDataLoader;

    @Inject
    public StreetNamesStatisticsLoader(FlatListDataLoader flatListDataLoader) {
        this.flatListDataLoader = flatListDataLoader;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        List<String> streetNames = flatListDataLoader.loadListFromFile(STREET_NAMES_FILE);
        return contactStatistics.withStreetNames(streetNames);
    }
}
