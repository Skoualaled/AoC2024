package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day11 {

    private static final List<Long> stones = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day11.in");
        readFile(input);
        part1();
        part2();
    }

    private static void part1(){
        long total = blink(25);
        System.out.println(total);
    }

    private static void part2() {
        long total = blink(75);
        System.out.println(total);
    }

    private static void readFile(File file){
        try {
            Scanner input = new Scanner(file);
            while(input.hasNextLong()) {
                stones.add(input.nextLong());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static List<Long> applyRule(Long stone){
        List<Long> newStones = new ArrayList<>();
        String stoneStr = stone.toString();
        if(stone==0L){
            newStones.add(1L);
        }
        else if(stoneStr.length()%2==0){
            Long valL = Long.parseLong(stoneStr.substring(0,stoneStr.length()/2));
            Long valR = Long.parseLong(stoneStr.substring(stoneStr.length()/2));
            newStones.add(valL);
            newStones.add(valR);
        }else{
            newStones.add(stone*2024);
        }
        return newStones;
    }
    private static long blink(int nbBlink){
        HashMap<Long, Long> stoneCount = new HashMap<>();
        // init stones
        for(Long s : stones){
            if (stoneCount.containsKey(s)) stoneCount.put(s, stoneCount.get(s)+1L);
            else stoneCount.put(s, 1L);
        }
        // par blink
        for (int i=0; i < nbBlink; i++){
            HashMap<Long, Long> newCount = new HashMap<>();
            for(Long k : stoneCount.keySet()){
                List<Long> newStones = applyRule(k);
                for(Long s : newStones){
                    if (newCount.containsKey(s)) newCount.put(s, newCount.get(s) + stoneCount.get(k));
                    else newCount.put(s, stoneCount.get(k));
                }
            }
            stoneCount = newCount;
        }
        long total = 0L;
        for(Long k : stoneCount.keySet()){
            total += stoneCount.get(k);
        }
        return total;
    }

}
