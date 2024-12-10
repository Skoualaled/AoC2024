package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day10 {

    private static final int[][] map = new int[40][40];

    public static void main(String[] args) {
        File file = new File("src/main/resources/day10.in");
        readFile(file);
        part1();
        part2();
    }

    public static void readFile(File file){
        try {
            Scanner input = new Scanner(file);
            int lineIdx = 0;
            while(input.hasNext()){
                int[] trail = input.next().chars().map(x -> x - '0').toArray();
                map[lineIdx] = trail;
                lineIdx++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }
    // Position de fin de rando
    private static class Position{
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        // Override pour HashSet
         @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    //Check coordonnes dans map
    private static boolean inBound(int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length;
    }
    // Retourne Set des positions de fin possibles
    private static HashSet<Position> checkTrailsScore(int x, int y){
        HashSet<Position> trails = new HashSet<>();
        // fin de rando
        if (map[x][y] == 9) {
            trails.add(new Position(x,y));
            return trails;
        }
        int nextVal = map[x][y] + 1 ;
        // appel recursif sur les directions possibles
        if (inBound(x+1,y) && map[x+1][y] == nextVal) trails.addAll(checkTrailsScore(x+1,y));
        if (inBound(x,y+1) && map[x][y+1] == nextVal) trails.addAll(checkTrailsScore(x,y+1));
        if (inBound(x-1,y) && map[x-1][y] == nextVal) trails.addAll(checkTrailsScore(x-1,y));
        if (inBound(x,y-1) && map[x][y-1] == nextVal) trails.addAll(checkTrailsScore(x,y-1));
        return trails;
    }

    private static void part1(){
        int trailScore = 0;
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[x].length; y++){
                if(map[x][y]==0) {
                    trailScore += checkTrailsScore(x, y).size(); // Score = nb de sommet atteignable
                }
            }
        }
        System.out.println("Part 1 : " + trailScore);
    }

    private static int checkTrailsRating(int x, int y) {
        int trailRating =0;
        if (map[x][y] == 9) {
            return 1;
        }
        int nextVal = map[x][y] + 1 ;
        if (inBound(x+1,y) && map[x+1][y] == nextVal) trailRating += checkTrailsRating(x+1,y);
        if (inBound(x,y+1) && map[x][y+1] == nextVal) trailRating += checkTrailsRating(x,y+1);
        if (inBound(x-1,y) && map[x-1][y] == nextVal) trailRating += checkTrailsRating(x-1,y);
        if (inBound(x,y-1) && map[x][y-1] == nextVal) trailRating += checkTrailsRating(x,y-1);
        return trailRating;
    }


    private static void part2(){
        int trailScore = 0;
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[x].length; y++){
                if(map[x][y]==0) {
                    trailScore += checkTrailsRating(x, y);
                }
            }
        }
        System.out.println("Part 2 : " + trailScore);
    }

}
