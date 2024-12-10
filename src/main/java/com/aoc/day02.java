package com.aoc;

import com.google.common.collect.Comparators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;


public class day02 {

    public static List<Reports> data = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day02.in");
        readFile(input);
        part1();
        part2();
    }

    public static void readFile(File file){
        try {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                String[] line = read.nextLine().split(" ");
                ArrayList<Integer> level = new ArrayList<>();
                for(String s : line){
                    level.add(Integer.parseInt(s));
                }

                Reports reports = new Reports(level);
                data.add(reports);
            }
            read.close();
        } catch(FileNotFoundException e) {
            System.err.println("File not found");
        }

    }

    public static void part1(){
        int safeCount = 0;
        for(Reports r : data){
            if (r.isSafe()) safeCount += 1;
        }
        System.out.println(safeCount);
    }

    public static void part2(){
        int safeCount = 0;
        for (Reports r : data){
            if (r.isSafe()) {
                safeCount += 1;
            }
            else {
                for(int i = 0; i < r.levels.size(); i++){
                    ArrayList<Integer> unsafeLevel = new ArrayList<>(r.levels);
                    unsafeLevel.remove(i);
                    Reports unsafed = new Reports(unsafeLevel);
                    if (unsafed.isSafe()) {
                        safeCount+=1;
                        break;
                    }

                }
            }
        }
        System.out.println(safeCount);
    }

    public static class Reports {
        private ArrayList<Integer> levels;

        public Reports(ArrayList<Integer> levels){
            this.levels = levels;
        }

        public boolean isSafe(){
            boolean sorted = Comparators.isInOrder(levels, Comparator.<Integer> naturalOrder()) || Comparators.isInOrder(levels, Comparator.<Integer> reverseOrder());
            if(!sorted) return false;
            for (int i = 0; i< levels.size()-1; i++){
                int compare = Math.abs(levels.get(i) - levels.get(i+1));
                if (compare > 3 || compare == 0 ) {
                    return false;
                }
            }
            return true;
        }

    }
}
