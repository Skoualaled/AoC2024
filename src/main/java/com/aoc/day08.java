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
    }

    private static void part1() {
        HashSet<Position> antinodes = new HashSet<>();
        for(String a : antennas.keySet()){
            ArrayList<Position> curAntennas = antennas.get(a);
            for(int i = 0; i < curAntennas.size(); i++){
                Position a1 = curAntennas.get(i);
                for(Position a2 : curAntennas.subList(i+1, curAntennas.size())){
                    int dx = a1.x - a2.x;
                    int dy = a1.y - a2.y;
                    if (inBound(a1.x+dx, a1.y+dy)) antinodes.add(new Position(a1.x +dx, a1.y+dy));
                    if (inBound(a2.x-dx, a2.y-dy)) antinodes.add(new Position(a2.x-dx, a2.y-dy));
                }
            }
        }
        System.out.println("Part 1 : " + antinodes.size());
    }

    private static boolean inBound(int x, int y){
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY;
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

    public record Position(int x, int y){}
}
