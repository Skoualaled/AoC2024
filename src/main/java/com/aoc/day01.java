package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day01 {

    public static void main(String[] args) {
        File input = new File("src/main/resources/day01.in");
        readFile(input);
        part1();
        part2();
    }

    private static final List<Integer> firstList = new ArrayList<>();
    private static final List<Integer> secondList = new ArrayList<>();

    private static void readFile(File file) {
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                String line = read.nextLine();
                String[] values = line.split(" +");
                firstList.add(Integer.parseInt(values[0]));
                secondList.add(Integer.parseInt(values[1]));
            }
            read.close();
            Collections.sort(firstList);
            Collections.sort(secondList);
        } catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static void part1() {

        Integer sum = 0 ;
        for (int i =0; i< firstList.size();i++) {
            sum += Math.abs(firstList.get(i) - secondList.get(i));
        }
        System.out.println(sum);
    }

    private static void part2() {
        Integer sum = 0;
        HashMap<Integer, Integer> secondListCount = new HashMap<Integer, Integer>();
        for (Integer val : secondList) {
            secondListCount.put(val, secondListCount.getOrDefault(val, 0) + 1);
        }
        for (Integer val : firstList) {
            sum += val * secondListCount.getOrDefault(val, 0);
        }
        System.out.println(sum);
    }
}