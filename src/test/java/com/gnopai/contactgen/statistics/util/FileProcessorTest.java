package com.gnopai.contactgen.statistics.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileProcessorTest {

    @Test
    public void testProcessLinesFromClasspathFile() throws Exception {
        // GIVEN
        FileProcessor testClass = new FileProcessor();
        List<String> results = new ArrayList<>();

        // WHEN
        testClass.processLinesFromClasspathFile ("/file_processor_test.txt", results::add);

        // THEN
        List<String> expectedResults = List.of("one", "two", "three", "four", "five");
        assertEquals(expectedResults, results);
    }

    @Test(expected = RuntimeException.class)
    public void testProcessLinesFromClasspathFile_noSuchFile() throws Exception {
        FileProcessor testClass = new FileProcessor();
        testClass.processLinesFromClasspathFile("no_such_file", null);
    }
}
