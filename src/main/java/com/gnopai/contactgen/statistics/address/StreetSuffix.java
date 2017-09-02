package com.gnopai.contactgen.statistics.address;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;

@Getter
public enum StreetSuffix {
    STREET("St", "Street"),
    AVENUE("Ave", "Avenue"),
    ROAD("Rd", "Road"),
    DRIVE("Dr", "Drive"),
    WAY("Way", "Way"),
    LANE("Ln", "Lane"),
    PLACE("Pl", "Place"),
    COURT("Ct", "Court"),
    CIRCLE("Cir", "Circle"),
    BOULEVARD("Blvd", "Boulevard"),
    PARKWAY("Pkwy", "Parkway");

    private final String shortName;
    private final String longName;

    StreetSuffix(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    @Override
    public String toString() {
        return shortName;
    }

    private static final List<StreetSuffix> VALUES_AS_LIST = ImmutableList.copyOf(values());
    public static List<StreetSuffix> valuesAsList() {
        return VALUES_AS_LIST;
    }
}
