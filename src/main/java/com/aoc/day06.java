package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day06 {

    static Grid InGrid;
    static Position start;
    static final HashSet<Position> path = new HashSet<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day06.in");
        readFile(input);
        part1();
        part2();
    }

    public enum Direction { N, S, E, W }

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
        while (guard.inGrid(InGrid)){
            path.add(guard.getPos());
            guard.move(InGrid);
        }
        System.out.println("Part 1 : " + path.size());
    }

    // se base sur le calcul du path en part1
    // Pour chaque position sur le chemin de sortie, on test si une obstruction provoque une boucle
    private static void part2(){
        int loopCount = 0;
        for(Position p : path){
            if(p.equals(start)) continue;
            Guard guard = new Guard(start, Direction.N);
            HashMap<Position, ArrayList<Direction>> newPath = new HashMap<>();
            InGrid.addObstruction(p);
            while (guard.inGrid(InGrid)) {
                Direction loopDir = guard.getDir();
                Position loopPos = guard.getPos();
                ArrayList<Direction> newDirs = newPath.getOrDefault(loopPos, new ArrayList<>());
                if (newDirs.contains(loopDir)){ // On repasse sur la même position avec la même direction
                    loopCount ++;
                    break;
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
            Position nextMove = pos.add(dir);
            return g.obstructions.contains(nextMove);
        }

        public void move(Grid g){
            while (blocked(g)) rotate();
            pos = pos.add(dir);
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

        public Position add(Direction d){
            return switch (d) {
                case N -> new Position(x - 1, y);
                case S -> new Position(x + 1, y);
                case E -> new Position(x, y + 1);
                case W -> new Position(x, y - 1);
            };

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
