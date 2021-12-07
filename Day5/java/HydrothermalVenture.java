package Day5.java;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

class HydrothermalVenture {

    int x1, x2, y1, y2;

    HydrothermalVenture(int... coordinates) {
        this.x1 = coordinates[0];
        this.y1 = coordinates[1];
        this.x2 = coordinates[2];
        this.y2 = coordinates[3];
    }

    @Override
    public String toString() {
        return "X1: " + x1 + " Y1: " + y1 + " X2: " + x2 + " Y2: " + y2;
    }

    static boolean isEqual(int p1, int p2) {
        return (p1 == p2) ? true : false;
    }

    public static void fillLineSegment(int[][] hvFloor, HydrothermalVenture pt) {
        int start = 0, end = 0;

        if (HydrothermalVenture.isEqual(pt.x1, pt.x2)) {
            start = (pt.y1 < pt.y2) ? pt.y1 : pt.y2;
            end = (pt.y1 > pt.y2) ? pt.y1 : pt.y2;

            for (int i = start; i <= end; i++) {
                hvFloor[pt.x1][i] += 1;
            }
        }
        if (HydrothermalVenture.isEqual(pt.y1, pt.y2)) {
            start = (pt.x1 < pt.x2) ? pt.x1 : pt.x2;
            end = (pt.x1 > pt.x2) ? pt.x1 : pt.x2;

            for (int i = start; i <= end; i++) {
                hvFloor[i][pt.y1] += 1;
            }
        }
    }

    static int overlappingPoints(int[][] hvFloor) {
        int count = 0;

        for (int i = 0; i < hvFloor.length; i++) {
            for (int j = 0; j < hvFloor[0].length; j++) {
                if (hvFloor[i][j] >= 2)
                    count++;
            }
        }

        return count;
    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day5/HydrothermalVentureCoord.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");
            ArrayList<HydrothermalVenture> hvArrayList = new ArrayList<HydrothermalVenture>();
            int xMAX = -9999, yMAX = -9999;

            for (int i = 0; i < fileLines.length; i++) {
                var s = fileLines[i].split(" -> ");
                var p1 = s[0].split(",");
                var p2 = s[1].split(",");
                int x1 = Integer.parseInt(p1[0]);
                int y1 = Integer.parseInt(p1[1]);
                int x2 = Integer.parseInt(p2[0]);
                int y2 = Integer.parseInt(p2[1]);
                int xCompare = 0, yCompare = 0;

                HydrothermalVenture hv = null;

                if (HydrothermalVenture.isEqual(x1, x2) || HydrothermalVenture.isEqual(y1, y2)) {
                    xCompare = (x1 >= x2) ? x1 : x2;
                    yCompare = (y1 >= y2) ? y1 : y2;

                    xMAX = (xMAX > xCompare) ? xMAX : xCompare;
                    yMAX = (yMAX > yCompare) ? yMAX : yCompare;

                    hv = new HydrothermalVenture(x1, y1, x2, y2);
                    hvArrayList.add(hv);
                    // System.out.println(hv.toString());
                }
            }
            // System.out.println(hvArrayList.size());
            // System.out.println("Max X Co-ordinate: " + xMAX);
            // System.out.println("Max Y Co-ordinate: " + yMAX);

            int[][] floorGrid = new int[xMAX + 1][yMAX + 1];
            // System.out.println("Grid Dimensions: [" + floorGrid.length + "*" +
            // floorGrid[0].length + "]");

            for (var lineSegment : hvArrayList) {
                HydrothermalVenture.fillLineSegment(floorGrid, lineSegment);
            }

            System.out.println(
                    "Points at which more than 2 lines overlap: " + HydrothermalVenture.overlappingPoints(floorGrid));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
