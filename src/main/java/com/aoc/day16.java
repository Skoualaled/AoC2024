package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day16 {
    private static final int size = 142;
    private static final int[][] maze = new int[size][size];
    private static Reindeer initReindeer;
    private static Reindeer ending;
    public static void main(String[] args) {
        File input = new File("src/main/resources/day16.in");
        readFile(input);
        part1();
    }

    private static void part1() {
        System.out.println(pathing());
    }

    private static boolean inbound(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public static int pathing(){
        Stack<Reindeer> queue = new Stack<>();
        queue.add(initReindeer);
        // Distances
        int[][] dist = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[initReindeer.x][initReindeer.y] = 0;
        while (!queue.isEmpty()) {
            Reindeer curR = queue.pop();
            // N
            if(inbound(curR.x-1, curR.y) && maze[curR.x-1][curR.y] != 1){
                int newDist = dist[curR.x][curR.y] + (curR.d == Dir.N ? 1 : 1001);
                if (newDist < dist[curR.x-1][curR.y]){
                    queue.add(new Reindeer(curR.x-1, curR.y, Dir.N));
                    dist[curR.x-1][curR.y] = newDist;
                }
            }
            // S
            if(inbound(curR.x+1, curR.y) && maze[curR.x+1][curR.y] != 1){
                int newDist = dist[curR.x][curR.y] + (curR.d == Dir.S ? 1 : 1001);
                if (newDist < dist[curR.x+1][curR.y]){
                    queue.add(new Reindeer(curR.x+1, curR.y, Dir.S));
                    dist[curR.x+1][curR.y] = newDist;
                }
            }
            // E
            if(inbound(curR.x, curR.y+1) && maze[curR.x][curR.y+1] != 1){
                int newDist = dist[curR.x][curR.y] + (curR.d == Dir.E ? 1 : 1001);
                if (newDist < dist[curR.x][curR.y+1]){
                    queue.add(new Reindeer(curR.x, curR.y+1, Dir.E));
                    dist[curR.x][curR.y+1] = newDist;
                }
            }
            // W
            if(inbound(curR.x, curR.y-1) && maze[curR.x][curR.y-1] != 1){
                int newDist = dist[curR.x][curR.y] + (curR.d == Dir.W ? 1 : 1001);
                if (newDist < dist[curR.x][curR.y-1]){
                    queue.add(new Reindeer(curR.x, curR.y-1, Dir.W));
                    dist[curR.x][curR.y-1] = newDist;
                }
            }
        }
        return dist[ending.x][ending.y];
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            int x =0;
            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split("");
                for(int y=0; y< line.length; y++){
                    maze[x][y] = line[y].equals("#") ? 1:0;
                    if (line[y].equals("S")) initReindeer = new Reindeer(x,y,Dir.E);
                    if (line[y].equals("E")) ending = new Reindeer(x,y,null);
                    }
                x++;
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public enum Dir{N,S,E,W}

    public record Reindeer(int x, int y, Dir d){}
}
