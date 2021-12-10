package Day10.java;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import java.nio.file.Files;

class SyntaxScoring {

    static long[] findSyntaxErrorAndMiddleScore(String[] inputStrings) {
        Stack<Character> chunkStack = new Stack<>();
        ArrayList<Integer> corruptLines = new ArrayList<>();
        ArrayList<Integer> incompleteLines = new ArrayList<>();
        HashMap<Character, Integer> chunkTypeCount = new HashMap<>();
        HashMap<Integer, String> iHm = new HashMap<>();
        HashMap<Integer, String> completionStrings = new HashMap<>();
        ArrayList<Long> autoCompleteScores = new ArrayList<>();

        long syntaxErroScore = 0, middleScore = 0;

        for (int i = 0; i < inputStrings.length; i++) {
            for (int j = 0; j < inputStrings[i].length(); j++) {
                Character ch = (Character) inputStrings[i].charAt(j);

                if (ch == '(' || ch == '<' || ch == '{' || ch == '[')
                    chunkStack.push(ch);
                else {
                    if (!chunkStack.isEmpty()) {
                        Character popChar = (Character) chunkStack.pop();

                        if (ch == ')' && popChar == '(')
                            continue;
                        else if (ch == '}' && popChar == '{')
                            continue;
                        else if (ch == ']' && popChar == '[')
                            continue;
                        else if (ch == '>' && popChar == '<')
                            continue;
                        else {
                            corruptLines.add(i);
                            if (chunkTypeCount.get(ch) == null)
                                chunkTypeCount.put(ch, 1);
                            else {
                                var currVal = chunkTypeCount.get(ch);
                                chunkTypeCount.put(ch, currVal + 1);
                            }
                            break;
                        }
                    }
                }
            }
            if (!corruptLines.contains(i))
                incompleteLines.add(i);
        }

        chunkStack.clear();

        for (var hm : chunkTypeCount.entrySet()) {
            if (hm.getKey() == '>')
                syntaxErroScore += hm.getValue() * 25137;
            else if (hm.getKey() == ']')
                syntaxErroScore += hm.getValue() * 57;
            else if (hm.getKey() == '}')
                syntaxErroScore += hm.getValue() * 1197;
            else
                syntaxErroScore += hm.getValue() * 3;
        }

        for (var iL : incompleteLines) {
            for (int j = 0; j < inputStrings[iL].length(); j++) {
                Character ch = (Character) inputStrings[iL].charAt(j);

                if (ch == '(' || ch == '<' || ch == '{' || ch == '[')
                    chunkStack.push(ch);
                else {
                    if (!chunkStack.isEmpty()) {
                        Character popChar = (Character) chunkStack.peek();

                        if (ch == ')' && popChar == '(')
                            chunkStack.pop();
                        else if (ch == '}' && popChar == '{')
                            chunkStack.pop();
                        else if (ch == ']' && popChar == '[')
                            chunkStack.pop();
                        else if (ch == '>' && popChar == '<')
                            chunkStack.pop();
                        else
                            continue;
                    }
                }
            }
            iHm.put(iL, chunkStack.toString());
            chunkStack.clear();
        }

        for (var hm : iHm.entrySet()) {
            String misMatchChunks = hm.getValue();
            int index = hm.getKey();
            char[] mmArr = misMatchChunks.toCharArray();

            for (int i = mmArr.length - 1; i > 0; i--) {
                if (mmArr[i] == '(') {
                    if (completionStrings.get(index) != null) {
                        String temp = completionStrings.get(index);
                        temp += ")";
                        completionStrings.put(index, temp);
                    } else {
                        completionStrings.put(index, ")");
                    }
                } else if (mmArr[i] == '{') {
                    if (completionStrings.get(index) != null) {
                        String temp = completionStrings.get(index);
                        temp += "}";
                        completionStrings.put(index, temp);
                    } else {
                        completionStrings.put(index, "}");
                    }
                } else if (mmArr[i] == '[') {
                    if (completionStrings.get(index) != null) {
                        String temp = completionStrings.get(index);
                        temp += "]";
                        completionStrings.put(index, temp);
                    } else {
                        completionStrings.put(index, "]");
                    }
                } else if (mmArr[i] == '<') {
                    if (completionStrings.get(index) != null) {
                        String temp = completionStrings.get(index);
                        temp += ">";
                        completionStrings.put(index, temp);
                    } else {
                        completionStrings.put(index, ">");
                    }
                }
            }
        }

        for (var hm : completionStrings.entrySet()) {
            char[] cString = hm.getValue().toCharArray();
            long score = 0;
            for (int i = 0; i < cString.length; i++) {
                if (cString[i] == ')') {
                    score = (score * 5) + 1;
                } else if (cString[i] == ']') {
                    score = (score * 5) + 2;
                } else if (cString[i] == '}') {
                    score = (score * 5) + 3;
                } else if (cString[i] == '>') {
                    score = (score * 5) + 4;
                }
            }
            autoCompleteScores.add(score);
        }

        Collections.sort(autoCompleteScores);
        int middleIndex = autoCompleteScores.size() / 2;

        middleScore = autoCompleteScores.get(middleIndex);

        return new long[] { syntaxErroScore, middleScore };

    }

    public static void main(String args[]) {
        try {
            Path fileName = Path.of("/home/divyaank/AdventOfCode21/Day10/SyntaxScoring.txt");
            String allContent = Files.readString(fileName);
            String[] fileLines = allContent.split("\n");

            long errorScore = SyntaxScoring.findSyntaxErrorAndMiddleScore(fileLines)[0];
            long middleScore = SyntaxScoring.findSyntaxErrorAndMiddleScore(fileLines)[1];
            System.out.println("Syntax Error Score: " + errorScore);
            System.out.println("Middle Score: " + middleScore);

        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}
