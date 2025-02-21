package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class day15 {

    private static final ArrayList<Position> walls = new ArrayList<>();
    private static final ArrayList<Position> boxes = new ArrayList<>();
    private static final ArrayList<Direction> path = new ArrayList<>();
    private static final ArrayList<Box> wideBoxes = new ArrayList<>();
    private static final ArrayList<Position> wideWalls = new ArrayList<>();
    private static Position startPos;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day15.in");
        readFile(input);
        part1(); // 1430439
        part2(); // 1458740
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
                            wideWalls.add(new Position(x,y*2));
                            wideWalls.add(new Position(x,y*2+1));
                            break;
                        case 'O' :
                            boxes.add(new Position(x,y));
                            wideBoxes.add(new Box(new Position(x,y*2), new Position(x,y*2+1)));
                            break;
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
        Position robot = startPos;
        for (Direction d : path){
            robot = moveRobot(robot, d);
        }
        System.out.println("Part 1 : "+sumCoordinate(boxes));
    }

    private static void part2() {
        Position wideRobot = new Position(startPos.x, startPos.y*2);
        for (Direction d : path){
            wideRobot = moveWideRobot(wideRobot, d);
        }
        System.out.println("Part 1 : "+sumCoordinate(wideBoxes.stream().map(b -> b.r).toList()));
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

    public static long sumCoordinate(List<Position> box){
        long res = 0;
        for(Position p : box){
            res += 100L * p.x + p.y;
        }
        return res;
    }

    public static class Box{
        Position r;
        Position l;

        public Box(Position r, Position l){
            this.r = r;
            this.l = l;
        }

        public Box add(Direction d){
            return new Box(r.add(d), l.add(d));
        }

        public boolean block(Position p){
            return (p.x == r.x || p.x == l.x) && (p.y == r.y || p.y == l.y);
        }

        public boolean collide(Box nBox){
            return r.equals(nBox.l) || r.equals(nBox.r) || l.equals(nBox.r) || l.equals(nBox.l);
        }
    }

    public static HashSet<Box> getStackedBoxes(Box b, Direction d){
        HashSet<Box> res = new HashSet<>();
        Set<Box> nextB = wideBoxes.stream().filter(box -> box.collide(b.add(d)) && !box.equals(b)).collect(Collectors.toSet());
        for(Box collided : nextB){
            res.addAll(getStackedBoxes(collided, d));
        }
        res.add(b);
        return res;
    }

    public static Position moveRobot(Position p, Direction d){
        Position newPos = p.add(d);
        if (walls.contains(newPos)) return p;
        if (boxes.contains(newPos)){
            Position nextBox = newPos.add(d);
            while(boxes.contains(nextBox)){
                nextBox = nextBox.add(d);
            }
            if (!walls.contains(nextBox)) {
                boxes.remove(newPos);
                boxes.add(nextBox);
                return newPos;
            }else return p;
        }
        return newPos;
    }

    public static Position moveWideRobot(Position p, Direction d){
        Position newPos = p.add(d);
        if (wideWalls.contains(newPos)) return p;
        Optional<Box> blockedBox = wideBoxes.stream().filter(b -> b.block(newPos)).findFirst(); // si box exist, elle est unique
        if (blockedBox.isPresent()){
            Box nextBox = blockedBox.get();
            HashSet<Box> stack = getStackedBoxes(nextBox, d);
            for(Box checkBox : stack){
                if(wideWalls.contains(checkBox.l.add(d)) || wideWalls.contains(checkBox.r.add(d))) return p;
            }
            wideBoxes.removeAll(stack);
            wideBoxes.addAll(stack.stream().map(b -> b.add(d)).toList());
        }
        return newPos;
    }
}