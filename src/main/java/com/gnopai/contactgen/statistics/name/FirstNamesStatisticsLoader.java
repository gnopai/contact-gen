package com.gnopai.contactgen.statistics.name;

import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.gnopai.contactgen.statistics.util.WeightedListBuilderByVolume;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class FirstNamesStatisticsLoader implements StatisticsLoader {
    private static final String FIRST_NAMES_FILE = "/first-names.txt";
    private final int weightScale;
    private final FileProcessor fileProcessor;

    @Inject
    public FirstNamesStatisticsLoader(@Named("decimal.scale") int weightScale, FileProcessor fileProcessor) throws Exception {
        this.weightScale = weightScale;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        Map<Integer, WeightedListBuilderByVolume<String>> maleListBuildersByDecade = new HashMap<>();
        Map<Integer, WeightedListBuilderByVolume<String>> femaleListBuildersByDecade = new HashMap<>();

        fileProcessor.processLinesFromClasspathFile(FIRST_NAMES_FILE, line -> {
            String[] columns = line.split("\\s+");
            int decade = Integer.parseInt(columns[0]);
            addDecadeIfNotExists(decade, maleListBuildersByDecade, femaleListBuildersByDecade);
            addNameCount(maleListBuildersByDecade, decade, columns[1], Integer.parseInt(columns[2]));
            addNameCount(femaleListBuildersByDecade, decade, columns[3], Integer.parseInt(columns[4]));
        });

        Map<Integer, WeightedList<String>> maleNamesByDecade = buildNamesByDecadeMap(maleListBuildersByDecade);
        Map<Integer, WeightedList<String>> femaleNamesByDecade = buildNamesByDecadeMap(femaleListBuildersByDecade);
        FirstNames firstNames = new FirstNames(maleNamesByDecade, femaleNamesByDecade);
        return contactStatistics.withFirstNames(firstNames);
    }

    private void addDecadeIfNotExists(int decade, Map<Integer, WeightedListBuilderByVolume<String>> maleListBuildersByDecade,
                                      Map<Integer, WeightedListBuilderByVolume<String>> femaleListBuildersByDecade) {
        if (!(maleListBuildersByDecade.containsKey(decade) || femaleListBuildersByDecade.containsKey(decade))) {
            maleListBuildersByDecade.put(decade, new WeightedListBuilderByVolume<>(weightScale));
            femaleListBuildersByDecade.put(decade, new WeightedListBuilderByVolume<>(weightScale));
        }
    }

    private void addNameCount(Map<Integer, WeightedListBuilderByVolume<String>> listBuildersByDecade, int decade, String name, int nameCount) {
        WeightedListBuilderByVolume<String> listBuilder = listBuildersByDecade.get(decade);
        listBuilder.addItem(capitalizeFully(name), nameCount);
    }

    private Map<Integer, WeightedList<String>> buildNamesByDecadeMap(Map<Integer, WeightedListBuilderByVolume<String>> listBuildersByDecade) {
        Map<Integer, WeightedList<String>> namesByDecade = new HashMap<>();
        for (Map.Entry<Integer, WeightedListBuilderByVolume<String>> entry : listBuildersByDecade.entrySet()) {
            int decade = entry.getKey();
            WeightedList<String> nameList = entry.getValue().build();
            namesByDecade.put(decade, nameList);
        }
        return namesByDecade;
    }
}
