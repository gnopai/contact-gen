package com.gnopai.contactgen.statistics.address;

import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.common.base.Splitter;
import com.google.inject.Inject;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.util.FileProcessor;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CityStatisticsLoader implements StatisticsLoader {
    private static final String CITIES_FILE = "/cities-by-state.txt";
    private final FileProcessor fileProcessor;

    @Inject
    public CityStatisticsLoader(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        Map<State, List<String>> citiesByState = new EnumMap<>(State.class);
        fileProcessor.processLinesFromClasspathFile(CITIES_FILE, line -> {
            List<String> columns = Splitter.on(",").trimResults().splitToList(line);
            State state = State.fromAbbreviation(columns.get(0));
            List<String> cities = columns.subList(1, columns.size());
            citiesByState.put(state, cities);
        });
        return contactStatistics.withCitiesByState(citiesByState);
    }

}
