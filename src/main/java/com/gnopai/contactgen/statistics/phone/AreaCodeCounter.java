package com.gnopai.contactgen.statistics.phone;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.Value;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.gnopai.contactgen.statistics.util.WeightedListBuilderByVolume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

public class AreaCodeCounter {
    private final int scale;
    private final Multiset<StateAreaCode> stateAreaCodes = HashMultiset.create();
    private final ArrayListMultimap<String, String> zipAreaCodes = ArrayListMultimap.create();

    public AreaCodeCounter(int scale) {
        this.scale = scale;
    }

    public void addAll(State state, String zipCode, List<String> areaCodes) {
        zipAreaCodes.putAll(zipCode, areaCodes);
        areaCodes.forEach(areaCode -> stateAreaCodes.add(new StateAreaCode(state, areaCode)));
    }

    public AreaCodes buildAreaCodes() {
        return new AreaCodes(zipAreaCodes, buildWeightedAreaCodesByState());
    }

    private Map<State, WeightedList<String>> buildWeightedAreaCodesByState() {
        Map<State, WeightedListBuilderByVolume<String>> buildersByState = new HashMap<>();
        stateAreaCodes.elementSet().stream()
                .sorted(comparing(StateAreaCode::getState).thenComparing(StateAreaCode::getAreaCode))
                .forEach(stateAreaCode -> {
                    WeightedListBuilderByVolume<String> listBuilder = buildersByState.computeIfAbsent(stateAreaCode.getState(), state -> new WeightedListBuilderByVolume<>(scale));
                    listBuilder.addItem(stateAreaCode.getAreaCode(), stateAreaCodes.count(stateAreaCode));
                });
        return buildersByState.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().build()));
    }

    @Value
    private static class StateAreaCode {
        State state;
        String areaCode;
    }

}
