package com.gnopai.contactgen.statistics.address;

import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.gnopai.contactgen.statistics.util.WeightedListBuilderByVolume;

public class StateStatisticsLoader implements StatisticsLoader {
    private static final String STATE_POPULATION_FILE = "/population-by-state.txt";
    private final int scale;
    private final FileProcessor fileProcessor;

    @Inject
    public StateStatisticsLoader(@Named("decimal.scale") int scale, FileProcessor fileProcessor) {
        this.scale = scale;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        WeightedListBuilderByVolume<State> stateListBuilder = new WeightedListBuilderByVolume<>(scale);

        fileProcessor.processLinesFromClasspathFile(STATE_POPULATION_FILE, line -> {
            String[] columns = line.split("\\s+");
            State state = State.fromAbbreviation(columns[0]);
            int population = Integer.parseInt(columns[1]);
            stateListBuilder.addItem(state, population);
        });

        WeightedList<State> states = stateListBuilder.build();
        return contactStatistics.withStates(states);
    }
}
