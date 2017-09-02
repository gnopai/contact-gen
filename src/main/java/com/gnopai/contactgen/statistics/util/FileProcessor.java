package com.gnopai.contactgen.statistics.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;

import static com.google.common.base.Throwables.propagate;

public class FileProcessor {

    public void processLinesFromClasspathFile(String fileName, Consumer<String> lineConsumer) {
        try (BufferedReader reader = getReaderForClasspathFile(fileName)) {
            String line = reader.readLine();
            while (line != null) {
                lineConsumer.accept(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    private BufferedReader getReaderForClasspathFile(String fileName) throws Exception {
        URI uri = getUriForClasspathFile(fileName);
        File file = new File(uri);
        return new BufferedReader(new FileReader(file));
    }

    private URI getUriForClasspathFile(String fileName) throws URISyntaxException, FileNotFoundException {
        URL url = FileProcessor.class.getResource(fileName);
        if (url == null) {
            throw new FileNotFoundException("File not found in classpath: " + fileName);
        }
        return url.toURI();
    }

}
