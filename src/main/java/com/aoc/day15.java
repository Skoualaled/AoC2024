package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class day15 {

    private static final ArrayList<Position> walls = new ArrayList<>();
    private static final ArrayList<Position> boxes = new ArrayList<>();
    private static final ArrayList<Direction> path = new ArrayList<>();
    private static Position startPos;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day15.in");
        readFile(input);
        part1();
        part2();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            int x = 0;
            while (scan.hasNextLine()){
                char[] line = scan.nextLine().toCharArray();
                for(int y =0; y< line.length;y++){
                    switch (line[y]) {
                        case '#' :
                            walls.add(new Position(x,y));
                            break;
                        case 'O' : boxes.add(new Position(x,y)); break;
                        case '@' : startPos = new Position(x,y); break;
                        case '<' : path.add(Direction.W); break;
                        case '^' : path.add(Direction.N); break;
                        case '>' : path.add(Direction.E); break;
                        case 'v' : path.add(Direction.S); break;
                    }
                }
                x++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1() {
        Robot astroBot = new Robot(startPos);
        for (Direction d : path){
            astroBot.move(d);
        }
        System.out.println(sumCoord());
    }

    private static void part2() {
    }

    public enum Direction{ N,S,E,W }

    public record Position(int x, int y){
        public Position add(Direction d){
            return switch (d) {
                case N -> new Position(x - 1, y);
                case E -> new Position(x, y + 1);
                case S -> new Position(x + 1, y);
                case W -> new Position(x, y - 1);
            };
        }
    }

    public static long sumCoord(){
        long res = 0;
        for(Position p : boxes){
            res += 100L * p.x + p.y;
        }
        return res;
    }

    public static class Robot{
        Position p;

        public Robot(Position p){
            this.p = p;
        }

        public void move(Direction d){
            Position newPos = p.add(d);
            if (walls.contains(newPos)) return;
            if (boxes.contains(newPos)){
                Position nextBox = newPos.add(d);
                while(boxes.contains(nextBox)){
                    nextBox = nextBox.add(d);
                }
                if (!walls.contains(nextBox)) {
                    boxes.remove(newPos);
                    boxes.add(nextBox);
                    this.p = newPos;
                }
            }else this.p = newPos;
        }
    }
}
