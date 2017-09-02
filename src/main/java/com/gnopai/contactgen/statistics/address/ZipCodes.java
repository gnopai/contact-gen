package com.gnopai.contactgen.statistics.address;

import com.google.common.collect.ArrayListMultimap;
import lombok.Value;
import com.gnopai.contactgen.model.State;

import java.util.List;

public class ZipCodes {
    private final ArrayListMultimap<CityState, String> zipCodesByCityState;

    public ZipCodes(ArrayListMultimap<CityState, String> zipCodesByCityState) {
        this.zipCodesByCityState = zipCodesByCityState;
    }

    public List<String> getZipCodes(String city, State state) {
        CityState cityState = new CityState(city, state);
        return zipCodesByCityState.get(cityState);
    }

    @Value
    static class CityState {
        String city;
        State state;

        public CityState(String city, State state) {
            this.city = city.toLowerCase();
            this.state = state;
        }
    }
}
