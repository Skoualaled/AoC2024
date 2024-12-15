package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day14 {

    private static final ArrayList<Robot> robotFleet = new ArrayList<>();
    private static final int bathX = 101;
    private static final int bathY = 103;
    private static final int qxLimit = (bathX)/2;
    private static final int qyLimit = (bathY)/2;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day14.in");
        readFile(input);
        solve();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            Pattern robotInit = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                Matcher match = robotInit.matcher(line);
                if(match.find()) {
                    Robot newRobot = new Robot(match.group(1), match.group(2), match.group(3), match.group(4));
                    robotFleet.add(newRobot);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long getSafetyFactor(){
        long q1 = robotFleet.stream().filter(robot -> robot.x < qxLimit && robot.y < qyLimit).count();
        long q2 = robotFleet.stream().filter(robot -> robot.x > qxLimit && robot.y < qyLimit).count();
        long q3 = robotFleet.stream().filter(robot -> robot.x < qxLimit && robot.y > qyLimit).count();
        long q4 = robotFleet.stream().filter(robot -> robot.x > qxLimit && robot.y > qyLimit).count();
        return q1*q2*q3*q4;
    }
    /**
     * Calcul par iteration via modulo arithmétique des positions
     * Pour la part 2 -> on calcule le nombre moyen voisin par robots
     * L'itération avec le plus de voisin correspond au plus gros regroupement de robots donc à l'image du sapin
     */
    private static void solve() {
        double avgNeighbors = 0.0;
        int xmasTree = 0;
        for(int i =1; i< 10000; i++){
            for (Robot r : robotFleet) {
                r.moveBy(1);
            }
            double avg = numberOfNeighbors();
            if(avg > avgNeighbors) {
                xmasTree = i;
                avgNeighbors = avg;
            }
            if(i == 100) System.out.println("Part1 : " + getSafetyFactor()); // part1 -> 100 seconds
        }
        System.out.println("Part 2 : " + xmasTree);
    }

    private static double numberOfNeighbors(){
        long n = 0;
        for(Robot r : robotFleet){
            n += robotFleet.stream().filter(r::isNeighbor).count();
        }
        return (double) n / robotFleet.size();
    }

    public static class Robot{
        int x;
        int y;
        int dx;
        int dy;

        public Robot(String x, String y, String dx, String dy){
            this.x=Integer.parseInt(x);
            this.y= Integer.parseInt(y);
            this.dx= Integer.parseInt(dx);
            this.dy= Integer.parseInt(dy);
        }

        public void moveBy(int sec){
            this.x = Math.floorMod(x+(sec*dx), bathX);
            this.y = Math.floorMod(y+(sec*dy), bathY);
        }

        public boolean isNeighbor(Robot r){
            return !r.equals(this) && ((Math.abs(r.x-x) == 1 && r.y==y) || (Math.abs(r.y-y) == 1 && r.x==x));
        }
    }
}
