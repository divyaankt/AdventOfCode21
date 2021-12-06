package Day1.java;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

class ModifiedSonarSweep {
    public static void main(String args[]) {

        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day1/SonarDepthMeasurements.txt");
            String allDepths = Files.readString(fileName);
            String[] depths = allDepths.split("\n");
            int currentSubSum, nextSubSum, consecutiveSubSumDiff, inCount = 0;

            int[] depthArray = new int[depths.length];

            for (int i = 0; i < depthArray.length; i++) {
                depthArray[i] = Integer.valueOf(depths[i]);
            }

            for (int i = 1; i < depthArray.length - 3; i++) {
                currentSubSum = depthArray[i] + depthArray[i + 1] + depthArray[i + 2];
                nextSubSum = currentSubSum - depthArray[i] + depthArray[i + 3];

                consecutiveSubSumDiff = nextSubSum - currentSubSum;

                if (consecutiveSubSumDiff > 0)
                    inCount += 1;
            }

            System.out.println("Number of Increasing 3-Window Depth Measurements: " + inCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
