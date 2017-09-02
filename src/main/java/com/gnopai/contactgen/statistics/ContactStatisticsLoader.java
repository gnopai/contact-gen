package com.gnopai.contactgen.statistics;

import com.google.inject.Inject;

import java.util.List;

public class ContactStatisticsLoader implements StatisticsLoader {
    private final List<StatisticsLoader> statisticsLoaders;

    @Inject
    public ContactStatisticsLoader(List<StatisticsLoader> statisticsLoaders) {
        this.statisticsLoaders = statisticsLoaders;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        for (StatisticsLoader statisticsLoader : statisticsLoaders) {
            contactStatistics = statisticsLoader.loadStatistics(contactStatistics);
        }
        return contactStatistics;
    }

}
