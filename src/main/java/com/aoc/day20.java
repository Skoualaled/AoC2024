package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day20 {
    private static int size;
    private static final HashSet<Position> blocks = new HashSet<>();
    private static Position start;
    private static Position end;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day20.in");
        readFile(input);
        part1();
        part2();
    }

    private static void part1() {
        long maxPath = pathing(blocks);
        Map<Long, Long> scores = new HashMap<>();
        for(Position b : blocks){
            HashSet<Position> subBlock = new HashSet<>(blocks);
            subBlock.remove(b);
            long save = maxPath-pathing(subBlock);
            if (scores.containsKey(save)) scores.put(save, scores.get(save)+1);
            else scores.put(save,1L);
        }
        long total=0L;
        for(Long val : scores.keySet()){
            if (val >= 100L) total += scores.get(val);
        }
        System.out.println("Part 1 : " +total);
    }

    private static void part2(){
    }

    private static boolean inbound(Position p) {
        return p.x >= 0 && p.x < size && p.y >= 0 && p.y < size;
    }

    public static int pathing(HashSet<Position> track){
        Stack<Position> queue = new Stack<>();
        queue.add(start);

        List<Position> dir = List.of(new Position(0,1), new Position(0,-1), new Position(1,0), new Position(-1,0));

        int[][] dist = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        dist[start.x][start.y] = 0;
        while (!queue.isEmpty()) {
            Position p = queue.pop();
            for (Position d : dir) {
                Position np = new Position(p.x + d.x, p.y + d.y);
                int newD =  dist[p.x][p.y] + 1;
                if ( inbound(np) && dist[np.x][np.y] > newD && !track.contains(np)) {
                    dist[np.x][np.y] = dist[p.x][p.y] + 1;
                    queue.add(np);
                }
            }
        }
        return dist[end.x][end.y];
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            int x =0;
            while(scan.hasNextLine()){
                char[] line = scan.nextLine().toCharArray();
                for(int y=0; y< line.length; y++){
                    if (line[y]=='#') blocks.add(new Position(x,y));
                    if (line[y]=='S') start = new Position(x,y);
                    if (line[y]=='E') end = new Position(x,y);
                }
                x++;
            }
            size = x;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public record Position(int x, int y){}
}
