package com.gnopai.contactgen.statistics;

import com.gnopai.contactgen.model.Gender;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.address.ZipCodes;
import com.gnopai.contactgen.statistics.name.FirstNames;
import com.gnopai.contactgen.statistics.phone.AreaCodes;
import com.gnopai.contactgen.statistics.util.Range;
import com.gnopai.contactgen.statistics.util.WeightedList;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

import java.util.List;
import java.util.Map;

@Value
@Wither
@Builder
@NonFinal
public class ContactStatistics {
    WeightedList<Range> ageRanges;
    FirstNames firstNames;
    WeightedList<String> lastNames;

    List<String> streetNames;
    WeightedList<State> states;
    Map<State, List<String>> citiesByState;
    ZipCodes zipCodes;

    AreaCodes areaCodes;

    public static ContactStatistics empty() {
        return builder().build();
    }

    public List<String> getCitiesForState(State state) {
        return citiesByState.get(state);
    }

    public List<String> getZipCodesForCity(String city, State state) {
        return zipCodes.getZipCodes(city, state);
    }

    public WeightedList<String> getFirstNameList(Gender gender, int decade) {
        return firstNames.getFirstNameList(gender, decade);
    }
}
