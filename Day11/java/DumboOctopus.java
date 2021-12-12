package Day11.java;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.Files;

class DumboOctopus {

    static int numSteps = 0;
    static int stepNo = 0;
    static long numFlashes = 0;
    static int[][] energyMatrix;
    static int[][] syncEnergyMatrix;
    static boolean[][] hasFlashed;

    static boolean isValidIndex(int index) {
        return (index < DumboOctopus.energyMatrix.length && index >= 0);
    }

    static void increment(int x, int y, int[][] eMatrix) {
        if (hasFlashed[x][y]) {
            return;
        }

        eMatrix[x][y]++;

        if (eMatrix[x][y] > 9) {
            DumboOctopus.numFlashes++;
            eMatrix[x][y] = 0;
            hasFlashed[x][y] = true;

            incrementNeighbours(x, y, eMatrix);
        }
    }

    static void incrementNeighbours(int x, int y, int[][] eMatrix) {

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;
                int adj_x = x + i;
                int adj_y = y + j;

                if (!isValidIndex(adj_x) || !isValidIndex(adj_y))
                    continue;

                increment(adj_x, adj_y, eMatrix);
            }
        }
    }

    static void flashStep(int[][] eMatrix) {
        hasFlashed = new boolean[eMatrix.length][eMatrix.length];

        for (int i = 0; i < eMatrix.length; i++) {
            for (int j = 0; j < eMatrix.length; j++) {
                increment(i, j, eMatrix);
            }
        }

    }

    static void flashStepSynchronous(int[][] eMatrix) {
        DumboOctopus.numFlashes = 0;
        while (true) {

            flashStep(eMatrix);

            int allSum = 0;

            for (int i = 0; i < eMatrix.length; i++) {
                for (int j = 0; j < eMatrix.length; j++) {
                    allSum += eMatrix[i][j];
                }
            }

            if (allSum == 0)
                break;
            DumboOctopus.stepNo++;
        }
    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day11/DumboOctopus.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");
            DumboOctopus.energyMatrix = new int[fileLines.length][fileLines[0].length()];
            DumboOctopus.syncEnergyMatrix = new int[fileLines.length][fileLines[0].length()];

            for (int i = 0; i < fileLines.length; i++) {
                for (int j = 0; j < fileLines.length; j++) {
                    DumboOctopus.energyMatrix[i][j] = Integer.parseInt(fileLines[i].charAt(j) + "");
                    DumboOctopus.syncEnergyMatrix[i][j] = Integer.parseInt(fileLines[i].charAt(j) + "");
                }
            }

            for (int i = 0; i < (DumboOctopus.energyMatrix.length * DumboOctopus.energyMatrix.length); i++) {
                System.out.println("Step: " + i);
                DumboOctopus.flashStep(DumboOctopus.energyMatrix);
            }

            System.out.println("Total No of Flashes: " + DumboOctopus.numFlashes);
            flashStepSynchronous(DumboOctopus.syncEnergyMatrix);

            System.out.println("Synchronous Step occurs at Step No: " + (DumboOctopus.stepNo + 1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}