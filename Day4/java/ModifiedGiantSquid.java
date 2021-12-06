package Day4.java;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;
import java.nio.file.Files;

class ModifiedGiantSquid {

    public static boolean isBingoComplete(int[][] bingoBoard) {

        for (int i = 0; i < 5; i++) {
            int horizontalSum = 0, verticalSum = 0;
            for (int j = 0; j < 5; j++) {
                horizontalSum += bingoBoard[i][j];
                verticalSum += bingoBoard[j][i];
            }
            if (horizontalSum == 0)
                return true;
            if (verticalSum == 0)
                return true;
        }

        return false;
    }

    public static int winningScore(int[][] bingoBoard, int recentInput) {
        int runningSum = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                runningSum += bingoBoard[i][j];
            }
        }

        return (runningSum * recentInput);
    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day4/Bingo.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");
            List<Integer> numList = new ArrayList<Integer>();
            List<int[][]> bingoBoards = new ArrayList<int[][]>();
            LinkedHashMap<Integer, Integer> lhm = new LinkedHashMap<>();

            int startPos = 2, numBoards = 0, winningScore = 0;

            for (String s : fileLines[0].split(",")) {
                numList.add(Integer.parseInt(s));
            }

            while (startPos < fileLines.length) {
                int[][] currentBoard = new int[5][5];

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        String[] tempStr = fileLines[startPos + i].trim().split("\\s+");

                        if (tempStr.length == 5) {
                            currentBoard[i][j] = Integer.parseInt(tempStr[j]);
                        }
                    }
                }

                bingoBoards.add(currentBoard);
                startPos += 6;
                numBoards++;
            }

            for (var s : numList) {
                int iter = 0;

                while (iter < numBoards) {
                    // System.out.println("Board Number: " + iter);
                    int[][] currentBoard = bingoBoards.get(iter);

                    for (int i = 0; i < currentBoard.length; i++) {
                        for (int j = 0; j < currentBoard.length; j++) {
                            if (currentBoard[i][j] == s.intValue()) {
                                currentBoard[i][j] = 0;
                                boolean isComplete = ModifiedGiantSquid.isBingoComplete(currentBoard);
                                // System.out.println(isComplete);

                                if (isComplete == true) {
                                    winningScore = ModifiedGiantSquid.winningScore(currentBoard, s);
                                    if (winningScore != 0 && !lhm.keySet().contains(iter + 1)) {
                                        // System.out.println("The winning score: " + winningScore);
                                        lhm.put(iter + 1, winningScore);
                                    }

                                }
                            }
                            // System.out.print(currentBoard[i][j] + " ");
                        }
                        // System.out.println();
                    }

                    iter++;
                }
            }

            System.out.println("Last Board No=Winning Score: " + lhm.entrySet().toArray()[lhm.size() - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
