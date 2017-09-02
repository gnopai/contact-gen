package com.gnopai.contactgen.statistics.util;

import lombok.Value;

@Value
public class Range {
    int low;
    int high;

    public static Range fromString(String rangeString) {
        String[] parts = rangeString.split("-");
        int low = Integer.parseInt(parts[0]);
        int high = Integer.parseInt(parts[1]);
        return new Range(low, high);
    }
}
