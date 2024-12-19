package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day18 {

    private static final ArrayList<Position> blocks = new ArrayList<>();
    private static final int size = 71;
    private static final int limit = 1024;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day18.in");
        readFile(input);
        part1();
        part2();
    }

    private static void part1() {
        List<Position> subBlock = blocks.subList(0,limit);
        int[][] maze = createMaze(subBlock);
        System.out.println("Part 1 : " + pathing(maze));
    }

    private static void part2(){
        int path = 0;
        int iteration = 0;
        while(path != -1){
            iteration++;
            List<Position> subBlock = blocks.subList(0,limit+iteration);
            int[][] maze = createMaze(subBlock);
            path = pathing(maze);
        }
        System.out.println("Part 2 : " + blocks.get(limit+iteration-1));
    }

    private static int[][] createMaze(List<Position> obstacles){
        int[][] maze = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(maze[i], 0);
        }
        for(Position p : obstacles){
            maze[p.x][p.y] = 1;
        }
        return maze;
    }

    private static boolean inbound(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public static int pathing(int[][] maze){
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0});
        // Direction
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        // Distances
        int[][] dist = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(dist[i], -1);
        }
        dist[0][0] = 0;
        while (!queue.isEmpty()) {
            // case a checker
            int[] p = queue.poll();
            int x = p[0];
            int y = p[1];
            // pour chaque voisin
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                // si inbound + non visité
                if ( inbound(nx, ny) && dist[nx][ny] == -1 && maze[nx][ny] == 0) {
                    dist[nx][ny] = dist[x][y] + 1;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        return dist[size-1][size-1];
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()){
                int[] line = Arrays.stream(scan.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                blocks.add(new Position(line[0], line[1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private record Position(int x, int y){}
}
