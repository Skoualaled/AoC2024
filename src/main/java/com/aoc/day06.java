package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day06 {

    static Grid InGrid;

    static Position start;
    static HashSet<Position> path = new HashSet<>();

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
            path.add(new Position(guard.pos));
            guard.move(InGrid);
        }
        System.out.println("Part 1 : " + path.size());
    }

    private static void part2(){
        int looped = 0;
        Grid blockedGrid = new Grid(InGrid);
        HashSet<Position> loopedPath = new HashSet<>();
        Guard guard = new Guard(new Position(start), Direction.N);
        for(Position p : path) {
            if (p == start) continue;
            loopedPath.clear();
            blockedGrid.obstructions.add(p);
            while(guard.inGrid(blockedGrid)){
                Position curPos = new Position(guard.getPos(), guard.getDir());
                if (loopedPath.contains(curPos)) {
                    looped++;
                    break;
                }
                loopedPath.add(curPos);
                guard.move(blockedGrid);
            }
            blockedGrid.obstructions.remove(p);
            guard.pos = new Position(start);
            guard.dir = Direction.N;
        }
        System.out.println("Part 2 : " + looped);
    }

    private static class Guard{
        Position pos;
        Direction dir;

        public Guard(Position pos, Direction dir){
            this.pos = pos;
            this.dir = dir;
        }

        public Guard(Guard g){
            this.pos = g.pos;
            this.dir = g.dir;
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
            Position nextMove = new Position(this.pos);
            nextMove.add(DirMap.get(dir));
            return g.obstructions.contains(nextMove);
        }

        public void move(Grid g){
            if (blocked(g)) this.rotate();
            this.pos.add(DirMap.get(dir));
        }

        public boolean inGrid(Grid g){
            return pos.x >= 0 && pos.x < g.sizeX && pos.y >= 0 && pos.y < g.sizeY;
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

        public Grid(Grid g){
            this.obstructions = g.obstructions;
            this.sizeX = g.sizeX;
            this.sizeY = g.sizeY;
        }

        public void addObstruction(Position p){
            this.obstructions.add(new Position(p));
        }
    }

    public static class Position{
        int x;
        int y;
        Direction dir;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Position(Position p, Direction dir){
            this.x = p.x;
            this.y = p.y;
            this.dir = dir;
        }

        public Position(Position p){
            this.x = p.x;
            this.y = p.y;
            this.dir = p.dir;
        }

        public void add(Position p){
            this.x += p.x;
            this.y += p.y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y && dir == position.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, dir);
        }
    }
}
