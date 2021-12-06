package Day3.java;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

class BinaryDiagnostic {
    public static void main(String args[]) {

        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day3/DiagnosticReport.txt");
            String allBinaryStrings = Files.readString(fileName);
            String[] binNums = allBinaryStrings.split("\n");
            int nRow = 1000, nCol = 12;
            int[][] binMatrix = new int[nRow][nCol];
            String gammaRate = "", epsilonRate = "";

            for (int i = 0; i < binNums.length; i++) {
                // System.out.println(binNums[i]);

                char[] binCharArray = new char[nCol];
                binCharArray = binNums[i].toCharArray();

                for (int j = 0; j < binCharArray.length; j++) {
                    binMatrix[i][j] = Integer.parseInt(Character.toString(binCharArray[j]));
                    // System.out.print(binMatrix[i][j]);
                }
                // System.out.println();
            }

            for (int j = 0; j < nCol; j++) {
                int colSum = 0;

                for (int i = 0; i < nRow; i++) {
                    colSum += binMatrix[i][j];
                }

                if (colSum > 500) {
                    gammaRate += "1";
                    epsilonRate += "0";
                } else {
                    gammaRate += "0";
                    epsilonRate += "1";
                }
            }

            System.out.println("Gamma Rate: " + Integer.parseInt(gammaRate, 2));
            System.out.println("Epsilon Rate: " + Integer.parseInt(epsilonRate, 2));
            System.out.println("Power Consumption: " + Integer.parseInt(gammaRate, 2) * Integer.parseInt(epsilonRate,
                    2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
