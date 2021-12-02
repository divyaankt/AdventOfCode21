package Day2.java;

import java.util.*;
import java.io.*;

class Dive {
    public static void main(String args[]) {
        String filePath = "/home/divyaank/AdventOfCode21/Day2/SubmarineMovement.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int hp = 0, dp = 0;

            while ((line = br.readLine()) != null) {
                String[] lineTokens = line.split(" ");
                int magnitude = Integer.parseInt(lineTokens[1]);

                switch (lineTokens[0]) {
                    case "forward":
                        hp += magnitude;
                        break;
                    case "up":
                        dp -= magnitude;
                        break;
                    case "down":
                        dp += magnitude;
                        break;
                    default:
                        break;
                }
            }

            System.out.println("Horizontal Co-ordinates: " + hp);
            System.out.println("Vertical Co-ordinates: " + dp);
            System.out.println("Final Result: " + (hp * dp));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
