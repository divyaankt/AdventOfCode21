package Day6.java;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

class Lanternfish {

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day6/Lanternfish.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");
            int numDays = 256, maxDuration = 9;
            long[] daysToReproduce = new long[9];
            long totalFishes = 0;

            for (String s : fileLines[0].split(",")) {
                daysToReproduce[Integer.parseInt(s)] += 1;
            }

            for (int i = 0; i < numDays; i++) {
                long dayZero = daysToReproduce[0];
                for (int j = 0; j < daysToReproduce.length - 1; j++) {
                    daysToReproduce[j] = daysToReproduce[(j + 1) % maxDuration];
                }
                daysToReproduce[6] += dayZero;
                daysToReproduce[8] = dayZero;
            }

            for (int j = 0; j < daysToReproduce.length; j++) {
                totalFishes += daysToReproduce[j];
            }

            System.out.println("Population after " + numDays + " Days: " + totalFishes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
