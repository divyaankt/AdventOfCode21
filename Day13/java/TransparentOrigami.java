package Day13.java;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.Files;

public class TransparentOrigami {

    static int numPts = 0, newNumPts = 0;
    static int xMax = -9999, yMax = -9999;
    // static int[][] ptGrid;
    static ArrayList<String> foldCommands = new ArrayList<String>();

    static int[][] foldLeft(int[][] ptGrid, String line) {
        String[] lineStr = line.split("=");
        String variable = lineStr[0];
        int constant = Integer.parseInt(lineStr[1]);
        int[][] newPtGrid = new int[ptGrid.length][];

        // System.out.println(variable);

        // System.out.println("Line: " + line);
        // System.out.println(constant);

        // System.out.println("Fold Left");
        for (int y = 0; y < ptGrid.length; y++) {
            newPtGrid[y] = new int[constant];

            for (int x = 0; x < constant; x++) {

                newPtGrid[y][x] = ptGrid[y][x];
            }
            for (int x = constant + 1; x < ptGrid[y].length; x++) {

                int newY = y;
                int newX = (2 * constant) - x;
                // System.out.println("New x-coordinate: " + newX + " for (y,x): (" + y + "," +
                // x + ")");

                newPtGrid[newY][newX] |= ptGrid[y][x];
                // System.out.println("newPtGrid" + newPtGrid[newY][newX]);
            }

        }

        return newPtGrid;

    }

    static int[][] foldUp(int[][] ptGrid, String line) {
        String[] lineStr = line.split("=");
        String variable = lineStr[0];
        int constant = Integer.parseInt(lineStr[1]);
        int[][] newPtGrid = new int[constant][];

        // System.out.println(variable);

        // System.out.println("Line: " + line);
        // System.out.println(constant);

        // System.out.println("Fold Up");
        for (int y = 0; y < constant; y++) {
            newPtGrid[y] = new int[ptGrid[y].length];
            for (int x = 0; x < ptGrid[y].length; x++) {
                newPtGrid[y][x] = ptGrid[y][x];
            }
        }
        for (int y = constant + 1; y < ptGrid.length; y++) {
            for (int x = 0; x < ptGrid[y].length; x++) {
                int newX = x;
                int newY = (2 * constant) - y;
                // System.out.println("New y-coordinate: " + newY + " for (y,x): (" + y + "," +
                // x + ")");

                newPtGrid[newY][newX] |= ptGrid[y][x];
                // System.out.println("newPtGrid" + newPtGrid[newY][newX]);
            }
        }

        return newPtGrid;

    }

    static int[][] fold(int[][] ptGrid, String line) {
        String[] lineStr = line.split("=");
        String variable = lineStr[0];
        int constant = Integer.parseInt(lineStr[1]);

        // System.out.println(variable);

        // System.out.println("Line: " + line);

        if (variable.equals("x")) {
            return foldLeft(ptGrid, line);
        } else {
            return foldUp(ptGrid, line);
        }

    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day13/TransparentOrigami.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");

            for (int i = 0; i < fileLines.length; i++) {
                if (fileLines[i].contains(",")) {
                    String[] pts = fileLines[i].split(",");
                    int x = Integer.parseInt(pts[0]);
                    int y = Integer.parseInt(pts[1]);

                    xMax = (xMax > x) ? xMax : x;
                    yMax = (yMax > y) ? yMax : y;
                }
            }

            int[][] ptGrid = new int[yMax + 1][xMax + 1];

            for (int i = 0; i < fileLines.length; i++) {
                if (fileLines[i].contains(",")) {
                    String[] pts = fileLines[i].split(",");
                    int x = Integer.parseInt(pts[0]);
                    int y = Integer.parseInt(pts[1]);

                    ptGrid[y][x] = 1;
                    numPts++;

                } else if (fileLines[i].contains("fold")) {
                    String[] s = fileLines[i].split(" ");
                    foldCommands.add(s[2]);
                }
            }

            // System.out.println("Maximum X-value: " + xMax);
            // System.out.println("Maximum Y-value: " + yMax);
            System.out.println("Initial No of Pts: " + numPts);

            // System.out.println("Initial Points:");
            // for (int y = 0; y < yMax + 1; y++) {
            // for (int x = 0; x < xMax + 1; x++) {
            // System.out.print(ptGrid[y][x] + " ");
            // }
            // System.out.println();
            // }

            // System.out.println("Lines:");
            // for (var s : foldCommands) {
            // System.out.println(s);
            // }

            for (int i = 0; i < foldCommands.size(); i++)
                ptGrid = fold(ptGrid, foldCommands.get(i));

            for (int y = 0; y < ptGrid.length; y++) {
                for (int x = 0; x < ptGrid[0].length; x++) {
                    if (ptGrid[y][x] == 1)
                        TransparentOrigami.newNumPts++;
                }

            }
            System.out.println("No of new Pts: " + TransparentOrigami.newNumPts);

            for (int y = 0; y < ptGrid.length; y++) {
                for (int x = 0; x < ptGrid[0].length; x++) {
                    System.out.print(ptGrid[y][x] == 1 ? '#' : '.');
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
