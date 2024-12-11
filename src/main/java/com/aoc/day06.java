package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day06 {

    static Grid InGrid;

    static Position start;
    static final HashMap<Position, HashSet<Direction>> path = new HashMap<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day06.in");
        readFile(input);
        part1();
        part2();
    }

    public enum Direction {
        N,S,E,W
    }

    public static Map<Direction, Position> DirMap = Map.of(Direction.N, new Position(-1,0)
                                                        ,Direction.S, new Position(1,0)
                                                        ,Direction.E, new Position(0,1)
                                                        ,Direction.W, new Position(0,-1));

    private static void readFile(File file){
        try {
            Scanner input = new Scanner(file);
            int gx = 0;
            int gy = 0;
            HashSet<Position> obstructions = new HashSet<>();
            while(input.hasNext()){
                char[] line = input.next().toCharArray();
                gy = line.length;
                for(int y = 0; y < line.length; y++){
                    if (line[y]=='#') obstructions.add(new Position(gx, y));
                    if (line[y] == '^') start = new Position(gx,y);
                }
                gx++;
            }
            InGrid = new Grid(obstructions, gx, gy);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1() {
        Guard guard = new Guard(new Position(start), Direction.N);
        while (guard.inGrid(InGrid)){
            HashSet<Direction> newDirs;
            Direction d = guard.getDir();
            Position p = new Position(guard.getPos());
            if (path.containsKey(p)) newDirs = path.get(p);
            else newDirs = new HashSet<>();
            newDirs.add(d);
            path.put(p, newDirs);
            guard.move(InGrid);
        }
        System.out.println("Part 1 : " + path.size());
    }

    private static void part2(){
        int loopCount = 0;
        HashMap<Position, HashSet<Direction>> newPath = new HashMap<>();
        for(Position p : path.keySet()){
            if(p.equals(start)) continue;
            Guard guard = new Guard(new Position(start), Direction.N);
            newPath.clear();
            InGrid.addObstruction(p);
            while (guard.inGrid(InGrid)) {
                HashSet<Direction> newDirs;
                Direction loopDir = guard.getDir();
                Position loopPos = new Position(guard.getPos());
                if (newPath.containsKey(loopPos) && newPath.get(loopPos).contains(loopDir) ) {
                    loopCount ++;
                    break;
                }
                if (newPath.containsKey(loopPos)) {
                    newDirs = newPath.get(loopPos);
                }
                else {
                    newDirs = new HashSet<>();
                }
                newDirs.add(loopDir);
                newPath.put(loopPos, newDirs);
                guard.move(InGrid);
            }
            InGrid.delObstruction(p);
        }
        System.out.println("Part 2 : " + loopCount);
    }

    private static class Guard{
        private Position pos;
        private Direction dir;

        public Guard(Position pos, Direction dir){
            this.pos = pos;
            this.dir = dir;
        }

        public Direction getDir() {
            return dir;
        }

        public Position getPos() {
            return pos;
        }

        public void rotate(){
            switch (this.dir) {
                case N : dir = Direction.E; break;
                case E : dir = Direction.S; break;
                case S : dir = Direction.W; break;
                case W : dir = Direction.N; break;
            }
        }

        public boolean blocked(Grid g){
            Position nextMove = pos.add(DirMap.get(dir));
            return g.obstructions.contains(nextMove);
        }

        public void move(Grid g){
            if (blocked(g)) rotate();
            this.pos = this.pos.add(DirMap.get(dir));
        }

        public boolean inGrid(Grid g){
            return pos.x > 0 && pos.x < g.sizeX && pos.y > 0 && pos.y < g.sizeY;
        }
    }

    private static class Grid{
        HashSet<Position> obstructions;
        int sizeX;
        int sizeY;

        public Grid(HashSet<Position> obs, int x, int y){
            this.obstructions = obs;
            this.sizeX = x;
            this.sizeY = y;
        }

        public void addObstruction(Position p){
            this.obstructions.add(p);
        }

        public void delObstruction(Position p){
            this.obstructions.remove(p);
        }
    }

    public static class Position{
        int x;
        int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Position(Position p){
            this.x = p.x;
            this.y = p.y;
        }

        public Position add(Position p){
            return new Position(x+p.x, y+p.y);
        }

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
}
