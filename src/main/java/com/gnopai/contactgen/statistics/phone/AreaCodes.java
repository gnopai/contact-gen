package com.gnopai.contactgen.statistics.phone;

import com.google.common.collect.ArrayListMultimap;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.util.WeightedList;

import java.util.List;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class AreaCodes {
    private final ArrayListMultimap<String, String> areaCodesByZipCode;
    private final Map<State, WeightedList<String>> areaCodesByState;

    public AreaCodes(ArrayListMultimap<String, String> areaCodesByZipCode, Map<State, WeightedList<String>> areaCodesByState) {
        this.areaCodesByZipCode = areaCodesByZipCode;
        this.areaCodesByState = areaCodesByState;
    }

    public List<String> getAreaCodesForZipCode(String zipCode) {
        return areaCodesByZipCode.get(zipCode);
    }

    public WeightedList<String> getAreaCodesForState(State state) {
        return areaCodesByState.get(state);
    }
}
