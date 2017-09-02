package com.gnopai.contactgen.statistics.util;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class FlatListDataLoaderTest {

    @Test
    public void testLoadListFromFile() throws Exception {
        // GIVEN
        FileProcessor fileProcessor = new MockFileProcessor(
                "Main",
                "Elm",
                "5th",
                "Whee",
                "El Ichio"
        );
        FlatListDataLoader testClass = new FlatListDataLoader(fileProcessor);

        // WHEN
        List<String> results = testClass.loadListFromFile("wheeeee");

        // THEN
        assertEquals(newArrayList("Main", "Elm", "5th", "Whee", "El Ichio"), results);
    }
}
