package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day19 {

    static List<String> patterns = new ArrayList<>();
    static ArrayList<String> designs = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day19.in");
        readFile(input);
        part1();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            patterns = Arrays.stream(scan.nextLine().split(", ")).toList();
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                if (!line.isBlank()) designs.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        int possibleDesigns = 0;
        StringBuilder towelRegex = new StringBuilder();
        for(String towel : patterns){
            towelRegex.append(towel).append("|");
        }
        String reg = "^(" + towelRegex.subSequence(0, towelRegex.length()-1) + ")*$";
        for(String d : designs){
            if(d.matches(reg)) possibleDesigns++;
        }
        System.out.println("Part 1 : " + possibleDesigns);
    }
}
