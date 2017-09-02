package com.gnopai.contactgen.generator.address;

import lombok.Value;
import com.gnopai.contactgen.statistics.address.UnitType;

@Value
public class Unit {
    String number;
    UnitType type;

    @Override
    public String toString() {
        return type.getShortName() + " " + number;
    }
}
