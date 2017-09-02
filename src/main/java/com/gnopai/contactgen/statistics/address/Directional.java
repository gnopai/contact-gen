package com.gnopai.contactgen.statistics.address;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;

@Getter
public enum Directional {
    NORTH("N", "North"),
    SOUTH("S", "South"),
    EAST("E", "East"),
    WEST("W", "West"),
    NORTHWEST("NW", "Northwest"),
    NORTHEAST("NE", "Northeast"),
    SOUTHWEST("SW", "Southwest"),
    SOUTHEAST("SE", "Southeast");

    private final String shortName;
    private final String longName;

    Directional(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    @Override
    public String toString() {
        return shortName;
    }

    private static final List<Directional> VALUES_AS_LIST = ImmutableList.copyOf(values());
    public static List<Directional> valuesAsList() {
        return VALUES_AS_LIST;
    }
}
