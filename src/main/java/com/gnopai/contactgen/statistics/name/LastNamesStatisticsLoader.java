package com.gnopai.contactgen.statistics.name;

import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.Value;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.gnopai.contactgen.statistics.util.WeightedListBuilderByVolume;

import java.util.Optional;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class LastNamesStatisticsLoader implements StatisticsLoader {
    private static final String LAST_NAMES_FILE = "/last-names.csv";
    private final int weightScale;
    private final FileProcessor fileProcessor;

    @Inject
    public LastNamesStatisticsLoader(@Named("decimal.scale") int weightScale, FileProcessor fileProcessor) {
        this.weightScale = weightScale;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        WeightedListBuilderByVolume<String> weightedListBuilder = new WeightedListBuilderByVolume<>(weightScale);

        fileProcessor.processLinesFromClasspathFile(LAST_NAMES_FILE, line -> {
            Optional<NameData> nameData = parseLine(line);
            nameData.ifPresent(n -> weightedListBuilder.addItem(n.getName(), n.getCount()));
        });

        WeightedList<String> lastNames = weightedListBuilder.build();
        return contactStatistics.withLastNames(lastNames);
    }

    private Optional<NameData> parseLine(String line) {
        try {
            String[] columns = line.split(",");
            String name = capitalizeFully(columns[0]);
            int count = Integer.parseInt(columns[2]);
            return Optional.of(new NameData(name, count));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Value
    private static class NameData {
        String name;
        int count;
    }
}
