package com.gnopai.contactgen.generator.random;

import com.google.common.base.Strings;
import com.gnopai.contactgen.statistics.util.WeightedList;

import java.math.BigDecimal;
import java.util.*;

public class StandardRandomGeneratorTester {

    private List<Integer> ints;
    private Map<Integer, Integer> results = new HashMap<>();
    private RandomGenerator randomGenerator = new StandardRandomGenerator(new Random());

    private void runWeightDistributionTest(String... weights) {
        buildInts(weights.length);
        WeightedList<Integer> weightedList = new WeightedList<>();

        int listCount = Math.min(ints.size(), weights.length);
        for (int i = 0; i < listCount; i++) {
            weightedList.add(ints.get(i), new BigDecimal(weights[i]));
        }

        for (int i = 1; i <= 100; i++) {
            int result = randomGenerator.selectWeightDistributedListItem(weightedList);
            addResult(result);
        }
        printResults();
    }

    private void runNormalDistributionTest(int count) {
        buildInts(count);

        for (int i = 1; i <= 1000; i++) {
            int result = randomGenerator.selectNormallyDistributedListItem(ints);
            addResult(result);
        }
        printResults();
    }

    private void buildInts(int count) {
        ints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ints.add(i);
        }
    }

    private void addResult(int result) {
        if (results.containsKey(result)) {
            results.put(result, results.get(result) + 1);
        }
        else {
            results.put(result, 1);
        }
    }

    private void printResults() {
        for (int inty : ints) {
            int count = results.getOrDefault(inty, 0);
            String stars = Strings.repeat("*", count);
            System.out.format("%3d: %s\n", inty, stars);
        }
    }

    public static void main(String[] args) {
        StandardRandomGeneratorTester tester = new StandardRandomGeneratorTester();
        tester.runWeightDistributionTest("0.1", "0.25", "0.5", "0.1", "0.05");
        System.out.print("\n\n\n");
        tester.runNormalDistributionTest(100);
    }

}

