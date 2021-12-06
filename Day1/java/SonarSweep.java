package Day1.java;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

class SonarSweep {
    public static void main(String args[]) {

        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day1/SonarDepthMeasurements.txt");
            String allDepths = Files.readString(fileName);
            String[] depths = allDepths.split("\n");
            int consecutiveDiff, inCount = 0;

            int[] depthArray = new int[depths.length];

            for (int i = 0; i < depthArray.length; i++) {
                depthArray[i] = Integer.valueOf(depths[i]);
            }

            for (int i = 1; i < depthArray.length; i++) {
                consecutiveDiff = depthArray[i] - depthArray[i - 1];

                if (consecutiveDiff > 0)
                    inCount += 1;
            }

            System.out.println("Number of Increasing Depth Measurements: " + inCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
