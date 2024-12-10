package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day03
{
    private static String data;
    private static final Pattern mult = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public static void main(String[] args) {
        File input = new File("src/main/resources/day03.in");
        readFile(input);
        part1(); // 161289189
        part2(); // 83595109
    }

    private static void readFile(File file) {
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                data += input.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }

    }

    private static long findMult(String input){
        long total = 0L;
        Matcher match = mult.matcher(input);
        while(match.find()){
            total += Long.parseLong(match.group(1)) * Long.parseLong(match.group(2));
        }
        return total;
    }

    private static void part1(){
        System.out.println(findMult(data));
    }

    private static void part2(){
        String cleanData = data.replaceAll("don't\\(\\)((?!do\\(\\)).)*", "");
        System.out.println(findMult(cleanData));
    }

}
