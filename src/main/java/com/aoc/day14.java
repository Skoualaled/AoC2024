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
        part1();
        part2();
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

    private static void part1() {
        for (Robot r : robotFleet) {
            r.moveBy(100);
        }
        long q1 = robotFleet.stream().filter(robot -> robot.x < qxLimit && robot.y < qyLimit).count();
        long q2 = robotFleet.stream().filter(robot -> robot.x > qxLimit && robot.y < qyLimit).count();
        long q3 = robotFleet.stream().filter(robot -> robot.x < qxLimit && robot.y > qyLimit).count();
        long q4 = robotFleet.stream().filter(robot -> robot.x > qxLimit && robot.y > qyLimit).count();
        long res = q1*q2*q3*q4;
        System.out.println("Part1 : " + res);
    }

    private static void part2() {

    }

    public static class Robot{
        int x;
        int y;
        int dx;
        int dy;
        public Robot(int x, int y, int dx, int dy){
            this.x=x;
            this.y=y;
            this.dx=dx;
            this.dy=dy;
        }

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
