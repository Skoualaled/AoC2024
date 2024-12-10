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
        Guard guard = new Guard(start, Direction.N);
        while (!guard.out(InGrid)){
            path.add(new Position(guard.pos));
            guard.move(InGrid);
        }
        System.out.println(path.size());
    }

    private static class Guard{
        Position pos;
        Direction dir;

        public Guard(Position pos, Direction dir){
            this.pos = pos;
            this.dir = dir;
        }

        public void rotate(){
            switch (this.dir) {
                case N : dir = Direction.E; break;
                case E : dir = Direction.S; break;
                case S : dir = Direction.W; break;
                case W : dir = Direction.N; break;
            }
        }

        public boolean blocked(Grid grid){
            Position nextMove = new Position(this.pos);
            nextMove.add(DirMap.get(dir));
            return grid.obstructions.contains(nextMove);
        }

        public void move(Grid grid){
            if (blocked(grid)) this.rotate();
            this.pos.add(DirMap.get(dir));
        }

        public boolean out(Grid grid){
            return pos.x == 0 || pos.x == grid.sizeX || pos.y == 0|| pos.y == grid.sizeY;
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

        public void add(Position p){
            this.x += p.x;
            this.y += p.y;
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
