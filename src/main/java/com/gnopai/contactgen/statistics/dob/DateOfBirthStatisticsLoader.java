package com.gnopai.contactgen.statistics.dob;

import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.Value;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.gnopai.contactgen.statistics.util.WeightedListBuilderByVolume;

public class DateOfBirthStatisticsLoader implements StatisticsLoader {
    private static final String AGE_DISTRIBUTION_FILE = "/population-by-age.txt";
    private final int scale;
    private final FileProcessor fileProcessor;

    @Inject
    public DateOfBirthStatisticsLoader(@Named("decimal.scale") int scale, FileProcessor fileProcessor) {
        this.scale = scale;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        WeightedListBuilderByVolume<Range> weightedListBuilder = new WeightedListBuilderByVolume<>(scale);
        fileProcessor.processLinesFromClasspathFile(AGE_DISTRIBUTION_FILE, line -> {
            RangeData rangeData = parseLine(line);
            weightedListBuilder.addItem(rangeData.getRange(), rangeData.getCount());
        });
        WeightedList<Range> ageRanges = weightedListBuilder.build();
        return contactStatistics.withAgeRanges(ageRanges);
    }

    private RangeData parseLine(String line) {
        String[] columns = line.split("\\s+");
        Range ageRange = Range.fromString(columns[0]);
        int ageRangeCount = Integer.parseInt(columns[1]);
        return new RangeData(ageRange, ageRangeCount);
    }

    @Value
    private static class RangeData {
        Range range;
        int count;
    }
}
