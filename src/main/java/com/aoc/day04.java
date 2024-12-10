package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class day04 {

    private static char[][] grid = new char[140][140];
    private static final Map<Character, Character> xmasMap = Map.of('X', 'M', 'M', 'A', 'A', 'S', 'S', 'E');


    public static void main(String[] args) {
        File input = new File("src/main/resources/day04.in");
        readFile(input);
        part1();
        part2();
    }

    private static void readFile(File file) {
        try {
            Scanner input = new Scanner(file);
            int lineId = 0;
            while (input.hasNext()) {
                char[] line = input.nextLine().toCharArray();
                grid[lineId] = line;
                lineId++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static boolean inBound(int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length;
    }

    private static int checkXMAS(int x, int y, int dx, int dy) {
        int fx = x + dx;
        int fy = y + dy;

        while (inBound(fx, fy)) {
            if (xmasMap.get(grid[fx - dx][fy - dy]) != grid[fx][fy]) return 0;
            if (grid[fx][fy] == 'S') return 1;
            fx += dx;
            fy += dy;
        }
        return 0;
    }

    private static void part1() {
        int nbXmas = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == 'X') {
                    nbXmas += checkXMAS(x, y, -1, -1)
                            + checkXMAS(x, y, -1, 0)
                            + checkXMAS(x, y, -1, 1)
                            + checkXMAS(x, y, 0, -1)
                            + checkXMAS(x, y, 0, 0)
                            + checkXMAS(x, y, 0, 1)
                            + checkXMAS(x, y, 1, -1)
                            + checkXMAS(x, y, 1, 0)
                            + checkXMAS(x, y, 1, 1);
                }
            }
        }
        System.out.println(nbXmas);
    }

    private static int checkCross(char tl, char dl, char dr, char tr) {
        if (tl != 'S' && tl != 'M') return 0;
        char inv = tl == 'S' ? 'M' : 'S';
        if (tl == tr && dl == inv && dr == dl) return 1;
        if (tl == dl && tr == inv && dr == tr) return 1;
        return 0;
    }

    private static void part2() {
        int nbXmas = 0;
        for (int x = 1; x < grid.length - 1; x++) {
            for (int y = 1; y < grid[x].length - 1; y++) {
                if (grid[x][y] == 'A') {
                    char tl = grid[x - 1][y - 1];
                    char dl = grid[x + 1][y - 1];
                    char dr = grid[x + 1][y + 1];
                    char tr = grid[x - 1][y + 1];
                    nbXmas += checkCross(tl, dl, dr, tr);
                }
            }
        }
        System.out.println(nbXmas);
    }
}
