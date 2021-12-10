package Day7.java;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.standard.Sides;

import java.nio.file.Files;

class WhalesAndCrabs {

    static double totalFuelBurnt(int numOfSteps) {
        return numOfSteps * (numOfSteps + 1) * 0.5;
    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day7/CrabPositions.txt");
            String allContent = Files.readString(fileName);
            // String[] fileLines = allContent.split("\n");
            HashMap<Integer, Integer> posMap = new HashMap<>();
            HashMap<Integer, Integer> fuelMap = new HashMap<>();
            HashMap<Integer, Integer> modifiedFuelMap = new HashMap<>();
            ArrayList<Integer> posList = new ArrayList<>();

            int min = 99999999, max = -99999999, minFuel = 99999999, modifiedMinFuel = 99999999;

            for (String pos : allContent.split(",")) {
                int val = Integer.parseInt(pos);

                if (val <= min)
                    min = val;
                if (val >= max)
                    max = val;

                if (posMap.get(val) == null) {
                    posMap.put(val, 1);
                } else {
                    int currVal = posMap.get(val);
                    posMap.put(val, currVal + 1);
                }

                posList.add(val);
            }

            // for (var x : posList) {
            // System.out.println(x);
            // }

            // for (var x : posMap.entrySet()) {
            // System.out.print(x.getKey() + ":" + x.getValue());
            // System.out.println();
            // }

            // System.out.println("Min Value: " + min);
            // System.out.println("Max Value: " + max);
            // System.out.println("Total: " + posMap.size());

            int[][] calcMatrix = new int[max + 1][max + 1];
            double[][] modifiedCalcMatrix = new double[max + 1][max + 1];

            for (int i = 0; i <= max; i++) {
                for (int j = 0; j <= max; j++) {
                    if (i == j)
                        calcMatrix[i][j] = 0;
                    else {
                        if (posMap.get(j) != null) {
                            int factor = posMap.get(j);
                            calcMatrix[i][j] = Math.abs(j - i) * factor;
                            modifiedCalcMatrix[i][j] = WhalesAndCrabs.totalFuelBurnt(Math.abs(j - i)) * factor;
                        }
                    }
                }
            }

            for (int i = 0; i <= max; i++) {
                int totalFuel = 0;
                int modifiedTotalFuel = 0;
                for (int j = 0; j <= max; j++) {
                    totalFuel += calcMatrix[i][j];
                    modifiedTotalFuel += modifiedCalcMatrix[i][j];
                }
                fuelMap.put(i, totalFuel);
                modifiedFuelMap.put(i, modifiedTotalFuel);
                // System.out.println();
            }

            for (var x : fuelMap.entrySet()) {
                if (minFuel > x.getValue())
                    minFuel = x.getValue();
            }

            for (var x : modifiedFuelMap.entrySet()) {
                if (modifiedMinFuel > x.getValue())
                    modifiedMinFuel = x.getValue();
            }

            // for (var x : fuelMap.entrySet()) {
            // System.out.print(x.getKey() + ":" + x.getValue());
            // System.out.println();
            // }

            System.out.println("Min Fuel: " + minFuel);
            System.out.println("New Min Fuel: " + modifiedMinFuel);

        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}
