package com.gnopai.contactgen.statistics.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WeightedListBuilderByVolume<T> {
    private final List<T> items = new ArrayList<>();
    private final List<BigDecimal> counts = new ArrayList<>();
    private final int scale;
    private BigDecimal sum;

    public WeightedListBuilderByVolume(int scale) {
        this.scale = scale;
        sum = new BigDecimal(0).setScale(scale, RoundingMode.HALF_UP);
    }

    public WeightedListBuilderByVolume<T> addItem(T item, int count) {
        items.add(item);
        BigDecimal countBigDecimal = new BigDecimal(count).setScale(scale, RoundingMode.HALF_UP);
        counts.add(countBigDecimal);
        sum = sum.add(countBigDecimal);
        return this;
    }

    public WeightedList<T> build() {
        WeightedList<T> weightedList = new WeightedList<>();

        for (int i = 0; i < items.size(); i++) {
            BigDecimal count = counts.get(i);
            BigDecimal weight = count.divide(sum, RoundingMode.HALF_UP);
            weightedList.add(items.get(i), weight);
        }

        return weightedList;
    }
}
