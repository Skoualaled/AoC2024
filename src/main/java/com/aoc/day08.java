package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day08 {

    public static HashMap<String, ArrayList<Position>> antennas = new HashMap<>();
    static int sizeX;
    static int sizeY;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day08.in");
        readFile(input);
        part1();
        part2();
    }

    private static void part1() {
        HashSet<Position> antinodes = new HashSet<>();
        for(String a : antennas.keySet()){
            ArrayList<Position> curAntennas = antennas.get(a);
            for(int i = 0; i < curAntennas.size(); i++){
                Position a1 = curAntennas.get(i);
                for(Position a2 : curAntennas.subList(i+1, curAntennas.size())){
                    Position dist = subP(a1, a2);
                    Position first = addP(a1, dist);
                    Position second = subP(a2, dist);
                    if (first.inBound()) antinodes.add(first);
                    if (second.inBound()) antinodes.add(second);
                }
            }
        }
        System.out.println("Part 1 : " + antinodes.size());
    }

    public static void part2(){
        HashSet<Position> antinodes = new HashSet<>();
        for(String a : antennas.keySet()){
            ArrayList<Position> curAntennas = antennas.get(a);
            for(int i = 0; i < curAntennas.size(); i++){
                Position a1 = curAntennas.get(i);
                antinodes.add(a1);
                for(Position a2 : curAntennas.subList(i+1, curAntennas.size())){
                    antinodes.add(a2);
                    Position dist = subP(a1, a2);
                    Position first = addP(a1, dist);
                    Position second = subP(a2, dist);
                    while(first.inBound()){
                        antinodes.add(first);
                        first = addP(first, dist);
                    }
                    while(second.inBound()){
                        antinodes.add(second);
                        second = subP(second, dist);
                    }
                }
            }
        }
        System.out.println("Part 2 : " + antinodes.size());
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            int x = 0;
            while (scan.hasNextLine()){
                String[] line = scan.nextLine().split("");
                for(int y =0; y< line.length;y++){
                    if (!Objects.equals(line[y], ".")){
                        Position p = new Position(x,y);
                        ArrayList<Position> positions = antennas.getOrDefault(line[y], new ArrayList<>());
                        positions.add(p);
                        antennas.put(line[y], positions);
                    }
                }
                x++;
                sizeY = line.length;
            }
            sizeX = x;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Position addP(Position p1, Position p2){
        return new Position(p1.x+p2.x, p1.y+p2.y);
    }
    public static Position subP(Position p1, Position p2){
        return new Position(p1.x-p2.x, p1.y-p2.y);
    }

    public record Position(int x, int y){
        public boolean inBound(){
            return x >= 0 && x < sizeX && y >= 0 && y < sizeY;
        }
    }
}
