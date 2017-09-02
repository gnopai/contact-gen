package com.gnopai.contactgen.statistics.util;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class FlatListDataLoader {
    private final FileProcessor fileProcessor;

    @Inject
    public FlatListDataLoader(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    public List<String> loadListFromFile(String fileName) {
        List<String> list = new ArrayList<>();
        fileProcessor.processLinesFromClasspathFile(fileName, list::add);
        return list;
    }
}
