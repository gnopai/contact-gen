package com.gnopai.contactgen.statistics.util;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@EqualsAndHashCode
@ToString
public class WeightedList<T> {
    private final List<Entry<T>> entries = new ArrayList<>();

    public void add(T item, BigDecimal weight) {
        Entry<T> entry = new Entry<>(item, weight);
        entries.add(entry);
    }

    public T get(int index) {
        return entries.get(index).getValue();
    }

    public BigDecimal getWeight(int index) {
        return entries.get(index).getWeight();
    }

    public List<Entry<T>> getEntries() {
        return ImmutableList.copyOf(entries);
    }

    public List<T> toList() {
        return entries.stream()
                .map(Entry::getValue)
                .collect(toUnmodifiableList());
    }

    @Value
    public static class Entry<T> {
        T value;
        BigDecimal weight;
    }

}
