package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.stripStart;

public class day11 {

    private static ArrayList<String> stones = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day11.in");

        part1(input);
        part2(input);
    }

    private static void part1(File input){
        readFile(input);
        for(int i = 0; i < 25; i++){
            blink();
        }
        System.out.println(stones.size());
    }

    private static void part2(File input) {

    }

    private static void readFile(File file){
        try {
            Scanner input = new Scanner(file);
            while(input.hasNext()) {
                stones.addAll(Arrays.asList(input.next().split(" ")));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private static void blink(){
        ArrayList<String> newStones = new ArrayList<>();
        for(String s : stones){
            if(Long.parseLong(s)==0L){
                newStones.add("1");
            }
            else if(s.length()%2==0){
                String valL = stripStart(s.substring(0,s.length()/2),"0");
                String valR = stripStart(s.substring(s.length()/2),"0");
                newStones.add(valL.isEmpty() ? "0" : valL);
                newStones.add(valR.isEmpty() ? "0" : valR);
            }else{
                long val = Long.parseLong(s) * 2024;
                newStones.add(Long.toString(val));
            }
        }
        stones = newStones;
    }

}
