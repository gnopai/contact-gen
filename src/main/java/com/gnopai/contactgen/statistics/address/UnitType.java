package com.gnopai.contactgen.statistics.address;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;

@Getter
public enum UnitType {
    APARTMENT("Apt", "Apartment"),
    UNIT("Unit", "Unit");

    private final String shortName;
    private final String longName;

    UnitType(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    @Override
    public String toString() {
        return shortName;
    }

    private static final List<UnitType> VALUES_AS_LIST = ImmutableList.copyOf(values());
    public static List<UnitType> valuesAsList() {
        return VALUES_AS_LIST;
    }
}
