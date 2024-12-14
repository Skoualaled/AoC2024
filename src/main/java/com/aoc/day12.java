package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day12 {

    private static final char[][] garden = new char[140][140];
    private static final ArrayList<ArrayList<Boolean>> checked = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day12.in");
        readFile(input);
        part1();
        //part2();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            int x = 0;
            while (scan.hasNext()){
                char[] line = scan.nextLine().toCharArray();
                System.arraycopy(line, 0, garden[x], 0, line.length);
                x++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean inBound(int x, int y) {
        return x >= 0 && x < garden.length && y >= 0 && y < garden.length;
    }

    private record Position(int x, int y) {}

    private static boolean validPlant(int x, int y, char c){
        return inBound(x, y) && garden[x][y] == c;
    }

    private static void part1() {
        int price = 0;
        // init check
        for(int i = 0; i< garden.length; i++){
            ArrayList<Boolean> l = new ArrayList<>();
            for (int j = 0; j < garden.length; j++){
                l.add(false);
            }
            checked.add(l);
        }

        for (int x = 0; x < garden.length; x++){
            for(int y = 0; y< garden.length; y++){
                if (checked.get(x).get(y)) continue;
                ArrayList<Position> queue = new ArrayList<>();
                queue.add(new Position(x,y));
                int perim = 0;
                int area = 0;
                while (!queue.isEmpty()) {
                    Position curPos = queue.get(0);
                    // si déjà check on passe
                    if (checked.get(curPos.x).get(curPos.y)){
                        queue.remove(0);
                        continue;
                    }
                    checked.get(curPos.x).set(curPos.y,true) ;
                    area++;
                    // si voisin valide on l'ajoute à la queue
                    if (validPlant(curPos.x - 1, curPos.y,garden[x][y] )) {
                        queue.add(new Position(curPos.x - 1, curPos.y));
                    } else perim ++;
                    if (validPlant(curPos.x + 1, curPos.y,garden[x][y] )) {
                        queue.add(new Position(curPos.x + 1, curPos.y));
                    } else perim ++;
                    if (validPlant(curPos.x, curPos.y-1,garden[x][y] )) {
                        queue.add(new Position(curPos.x, curPos.y - 1));
                    } else perim ++;
                    if (validPlant(curPos.x, curPos.y+1,garden[x][y] )) {
                        queue.add(new Position(curPos.x, curPos.y + 1));
                    } else perim ++;
                    // del de la queue la position controlé
                    queue.remove(curPos);
                }
                price += perim*area;
            }
        }
        System.out.println(price);

    }

}
