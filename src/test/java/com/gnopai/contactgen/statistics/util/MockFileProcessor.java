package com.gnopai.contactgen.statistics.util;

import java.util.List;
import java.util.function.Consumer;

public class MockFileProcessor extends FileProcessor {
    private final List<String> lines;

    public MockFileProcessor(String... lines) {
        this(List.of(lines));
    }

    public MockFileProcessor(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void processLinesFromClasspathFile(String fileName, Consumer<String> lineConsumer) {
        lines.forEach(lineConsumer);
    }
}
