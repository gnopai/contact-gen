package com.gnopai.contactgen.statistics.name;

import com.gnopai.contactgen.model.Gender;
import com.gnopai.contactgen.statistics.util.WeightedList;

import java.util.Map;

public class FirstNames {
    private final Map<Integer, WeightedList<String>> maleNamesByDecade;
    private final Map<Integer, WeightedList<String>> femaleNamesByDecade;

    public FirstNames(Map<Integer, WeightedList<String>> maleNamesByDecade, Map<Integer, WeightedList<String>> femaleNamesByDecade) {
        this.maleNamesByDecade = maleNamesByDecade;
        this.femaleNamesByDecade = femaleNamesByDecade;
    }

    public WeightedList<String> getFirstNameList(Gender gender, int decade) {
        Map<Integer, WeightedList<String>> namesByDecade = (gender == Gender.FEMALE) ? femaleNamesByDecade : maleNamesByDecade;
        return namesByDecade.get(decade);
    }
}
