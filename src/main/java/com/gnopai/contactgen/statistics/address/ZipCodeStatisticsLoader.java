package com.gnopai.contactgen.statistics.address;

import au.com.bytecode.opencsv.CSVParser;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.phone.AreaCodeCounter;
import com.gnopai.contactgen.statistics.util.FileProcessor;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class ZipCodeStatisticsLoader implements StatisticsLoader {
    private static final String ZIP_CODES_FILE = "/zip_code_database.csv";

    private final FileProcessor fileProcessor;
    private final CSVParser csvParser;
    private final Supplier<AreaCodeCounter> areaCodeCounterSupplier;

    @Inject
    public ZipCodeStatisticsLoader(FileProcessor fileProcessor, CSVParser csvParser, @Named("decimal.scale") int scale) {
        this.fileProcessor = fileProcessor;
        this.csvParser = csvParser;
        this.areaCodeCounterSupplier = () -> new AreaCodeCounter(scale);
    }

    @VisibleForTesting
    public ZipCodeStatisticsLoader(FileProcessor fileProcessor, CSVParser csvParser, Supplier<AreaCodeCounter> areaCodeCounterSupplier) {
        this.fileProcessor = fileProcessor;
        this.csvParser = csvParser;
        this.areaCodeCounterSupplier = areaCodeCounterSupplier;
    }

    @Override
    public ContactStatistics loadStatistics(ContactStatistics contactStatistics) {
        ArrayListMultimap<ZipCodes.CityState, String> zipCodesByCityState = ArrayListMultimap.create();
        AreaCodeCounter areaCodeCounter = areaCodeCounterSupplier.get();
        fileProcessor.processLinesFromClasspathFile(ZIP_CODES_FILE, line -> processLine(line, zipCodesByCityState, areaCodeCounter));
        return contactStatistics
                .withZipCodes(new ZipCodes(zipCodesByCityState))
                .withAreaCodes(areaCodeCounter.buildAreaCodes());
    }

    private void processLine(String line, Multimap<ZipCodes.CityState, String> zipCodesByCityState, AreaCodeCounter areaCodeCounter) {
        try {
            String[] fields = csvParser.parseLine(line);
            String zipCode = fields[0];
            String primaryCity = fields[2];
            State state = State.fromAbbreviation(fields[5]);
            if (state == null) {
                // a state we currently aren't tracking
                return;
            }

            ZipCodes.CityState cityState = new ZipCodes.CityState(primaryCity, state);
            zipCodesByCityState.put(cityState, zipCode);

            List<String> areaCodes = List.of(fields[8].split(","));
            areaCodeCounter.addAll(state, zipCode, areaCodes);

        } catch (IOException e) {
            log.warn("Error parsing zip code line: " + line, e);
        }
    }

}
