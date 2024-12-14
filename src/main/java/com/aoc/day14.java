package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static void solve() {
        for(int i =1; i< 10000; i++){
            for (Robot r : robotFleet) {
                r.moveBy(1);
            }
            if(isStraightLine()) printBathroom(robotFleet, i); // check visuel des fichiers de sorties à posteriori pour trouver le bon
            if(i == 100) System.out.println("Part1 : " + getSafetyFactor()); // part1 -> 100 seconds
        }
    }

    private static boolean isStraightLine() {
        for(Robot r : robotFleet){
            if (robotFleet.stream().filter(curR -> curR.x == r.x).count() > 15) return true;
            if (robotFleet.stream().filter(curR -> curR.y == r.y).count() > 15) return true;
        }
        return false;
    }

    public static void printBathroom(ArrayList<Robot> state, int iteration){
        String[][] plan = new String[101][103];
        for (Robot r : state){
            plan[r.x][r.y] = "#";
        }
        ArrayList<String> lines = new ArrayList<>();
        for (String[] strings : plan) {
            StringBuilder line = new StringBuilder();
            for (String string : strings) {
                line.append(string == null ? " " : "#");
            }
            lines.add(String.valueOf(line));
        }
        try {
            Path file = Paths.get("src/main/resources/day14_out/it_" + iteration);
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Erreur ecriture fichier");
        }
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

        @Override
        public String toString() {
            return "Robot{" +
                    "x=" + x +
                    ", y=" + y +
                    ", dx=" + dx +
                    ", dy=" + dy +
                    '}';
        }
    }
}
