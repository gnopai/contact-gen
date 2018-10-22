package com.gnopai.contactgen.generator.random;

import com.gnopai.contactgen.statistics.util.WeightedList;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/** Helpful utility for testing out how different random distributions perform. */
public class StandardRandomGeneratorTester {
    private RandomGenerator randomGenerator = new StandardRandomGenerator(new Random());

    public void runWeightDistributionTest(String... weights) {
        WeightedList<Integer> weightedList = new WeightedList<>();
        IntStream.range(0, weights.length)
                .forEach(i -> weightedList.add(i, new BigDecimal(weights[i])));
        Multiset<Integer> results = runTest(100, () -> randomGenerator.selectWeightDistributedListItem(weightedList));
        printResults(weights.length, results);
    }

    public void runNormalDistributionTest(int count) {
        List<Integer> ints = new ArrayList<>();
        IntStream.range(0, count).forEach(ints::add);
        Multiset<Integer> results = runTest(1000, () -> randomGenerator.selectNormallyDistributedListItem(ints));
        printResults(count, results);
    }

    private Multiset<Integer> runTest(int runCount, Supplier<Integer> resultSupplier) {
        Multiset<Integer> results = HashMultiset.create();
        IntStream.range(0, runCount)
                .forEach(i -> results.add(resultSupplier.get()));
        return results;
    }

    private void printResults(int count, Multiset<Integer> results) {
        IntStream.range(0, count).forEach(value -> {
            String stars = Strings.repeat("*", results.count(value));
            System.out.format("%3d: %s\n", value, stars);
        });
    }

    public static void main(String[] args) {
        StandardRandomGeneratorTester tester = new StandardRandomGeneratorTester();
        tester.runWeightDistributionTest("0.1", "0.25", "0.5", "0.1", "0.05");
        System.out.print("\n\n\n");
        tester.runNormalDistributionTest(100);
    }

}

