package Day14.java;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;

public class ExtendedPolymerization {

    static void applyInsertionRules(String polymerTemplate, HashMap<String, String> insRules, int numSteps) {
        String baseTemplate = polymerTemplate;
        String finalTemplate = "";
        long mostCommon = -999999999, leastCommon = 999999999;
        HashMap<Character, Long> countBase = new HashMap<>();
        HashMap<String, Long> pairMap = new HashMap<>();
        var lettersMap = polymerTemplate.chars().boxed().map(s -> Character.toString(s))
                .collect(Collectors.toMap(x -> x, x -> 1l, Long::sum));

        for (int i = 0; i < baseTemplate.length() - 1; i++) {
            pairMap.merge(baseTemplate.substring(i, i + 2), 1l, Long::sum);
        }

        // Set<String> baseSet = new HashSet<String>();

        // for (var hm : insRules.entrySet()) {
        // baseSet.add(hm.getValue());
        // }

        System.out.print("Difference between most and least common value count for " + numSteps + " steps: ");
        while (numSteps > 0) {
            HashMap<String, Long> newPairMap = new HashMap<>();

            for (var hm : pairMap.entrySet()) {

                String key = hm.getKey();
                Long count = hm.getValue();
                String middleString = insRules.get(key);
                String lhs = key.substring(0, 1);
                String rhs = key.substring(1, 2);

                newPairMap.merge(lhs + middleString, count, Long::sum);
                newPairMap.merge(middleString + rhs, count, Long::sum);
                lettersMap.merge(middleString, count, Long::sum);

            }

            pairMap = newPairMap;
            numSteps--;

        }

        for (var hm : lettersMap.entrySet()) {
            if (hm.getValue() < leastCommon)
                leastCommon = hm.getValue();
            else if (hm.getValue() >= mostCommon)
                mostCommon = hm.getValue();
        }

        lettersMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
        System.out.println();
        System.out.println(lettersMap);

    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day14/ExtendedPolymerization.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");
            HashMap<String, String> insRules = new HashMap<>();

            String polymerTemplate = fileLines[0];

            for (int i = 2; i < fileLines.length; i++) {

                String[] rule = fileLines[i].split(" -> ");
                insRules.put(rule[0], rule[1]);

            }

            // System.out.println("Polymer Template: " + polymerTemplate);
            // System.out.println("No of rules: " + insRules.size());
            // for (var s : insRules.entrySet()) {
            // System.out.println(s.getKey() + "->" + s.getValue());
            // }

            // System.out.println(insRules);

            applyInsertionRules(polymerTemplate, insRules, 10);
            applyInsertionRules(polymerTemplate, insRules, 40);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
