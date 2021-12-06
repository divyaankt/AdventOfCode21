package Day3.java;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.nio.file.Files;

class ModifiedBinaryDiagnostic {
    static StringBuilder oxyGen = new StringBuilder();
    static StringBuilder co2Scrubber = new StringBuilder();

    static int nCol = 12, nRow = 1000;

    public static void getOxyCO2Rating(String[] binNums, String OxyOrCO2) {
        ArrayList<String> listBinNums = new ArrayList<String>(Arrays.asList(binNums));
        int currPos = 0;

        while (listBinNums.size() != 1) {
            binNums = listBinNums.toArray(new String[listBinNums.size()]);
            int colSum = 0;
            int[][] binMatrix = new int[listBinNums.size()][nCol];

            for (int i = 0; i < binNums.length; i++) {

                char[] binCharArray = new char[nCol];
                binCharArray = binNums[i].toCharArray();

                for (int j = 0; j < binCharArray.length; j++) {
                    binMatrix[i][j] = Integer.parseInt(Character.toString(binCharArray[j]));
                }

            }

            Iterator<String> itr = listBinNums.iterator();
            double halfSum = Math.ceil((double) listBinNums.size() / 2);

            for (int i = 0; i < listBinNums.size(); i++) {
                colSum += binMatrix[i][currPos];
            }

            if (colSum >= (int) halfSum) {
                while (itr.hasNext()) {
                    String x = itr.next();

                    if (OxyOrCO2 == "OXY") {
                        if (x.charAt(currPos) != '1') {
                            itr.remove();
                        }
                    } else {
                        if (x.charAt(currPos) != '0') {
                            itr.remove();
                        }
                    }
                }
            } else {
                while (itr.hasNext()) {
                    String x = itr.next();
                    if (OxyOrCO2 == "OXY") {
                        if (x.charAt(currPos) != '0') {
                            itr.remove();
                        }
                    } else {
                        if (x.charAt(currPos) != '1') {
                            itr.remove();
                        }
                    }
                }
            }

            currPos++;
        }
        if (OxyOrCO2 == "OXY")
            ModifiedBinaryDiagnostic.oxyGen = new StringBuilder(listBinNums.get(0).toString());
        else
            ModifiedBinaryDiagnostic.co2Scrubber = new StringBuilder(listBinNums.get(0).toString());

    }

    public static void main(String args[]) {

        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day3/DiagnosticReport.txt");
            String allBinaryStrings = Files.readString(fileName);
            String[] binNums = allBinaryStrings.split("\n");

            ModifiedBinaryDiagnostic.getOxyCO2Rating(binNums, "OXY");
            ModifiedBinaryDiagnostic.getOxyCO2Rating(binNums, "CO2");

            System.out.println(
                    "Oxygen Generator Rating: " + Integer.parseInt(ModifiedBinaryDiagnostic.oxyGen.toString(), 2));
            System.out.println("CO2 Scrubber Rating: " +
                    Integer.parseInt(ModifiedBinaryDiagnostic.co2Scrubber.toString(), 2));
            System.out
                    .println("Life Support Rating: " + Integer.parseInt(ModifiedBinaryDiagnostic.oxyGen.toString(), 2) *
                            Integer.parseInt(ModifiedBinaryDiagnostic.co2Scrubber.toString(), 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
