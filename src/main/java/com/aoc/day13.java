package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day13 {

    private static final Pattern button = Pattern.compile("^Button (.): X\\+(\\d+), Y\\+(\\d+)$");
    private static final Pattern prize = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
    private static ArrayList<ClawMachine> machines = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day13.in");
        readFile(input);
        part1();
        part2();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            long ax, ay, bx, by, px, py;
            ax=ay= bx= by= px= py =0;
            while(scan.hasNext()){

                String line = scan.nextLine();
                Matcher buttonX = button.matcher(line);
                Matcher machinePrize = prize.matcher(line);
                if (buttonX.find()) {
                    if(buttonX.group(1).equals("A")) {
                        ax = Long.parseLong(buttonX.group(2));
                        ay = Long.parseLong(buttonX.group(3));
                    }else{
                        bx = Long.parseLong(buttonX.group(2));
                        by = Long.parseLong(buttonX.group(3));
                    }
                }
                if (machinePrize.find()) {
                    px = Long.parseLong(machinePrize.group(1));
                    py = Long.parseLong(machinePrize.group(2));
                    machines.add(new ClawMachine(ax,ay,bx,by,px,py));
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1() {
        long minToken = 0L;
        for(ClawMachine m : machines){
            long localMinToken = 400; // 100*3 + 100
            boolean winnableX = false;
            for(int a = 0; a <=100; a++){
                for (int b = 0; b <= 100; b++){
                    long tokens = a*3+b;
                    if (a*m.ax + b*m.bx == m.prizeX  && a*m.ay + b*m.by == m.prizeY && tokens < localMinToken){
                        localMinToken = tokens;
                        winnableX = true;
                    }
                }
            }
            if(winnableX ) {
                minToken += localMinToken ;
            }
        }
        System.out.println("Part 1 : " + minToken);
    }

    private static void part2() {
    }

    private static class ClawMachine{
        final long ax;
        final long ay;
        final long bx;
        final long by;
        final long prizeX;
        final long prizeY;

        public ClawMachine(long ax, long ay, long bx,long by, long prizeX, long prizeY){
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.prizeX = prizeX;
            this.prizeY = prizeY;
        }

        public ClawMachine(String ax, String ay, String bx,String by, String prizeX, String prizeY){
            this.ax = Long.parseLong(ax);
            this.ay = Long.parseLong(ay);
            this.bx = Long.parseLong(bx);
            this.by = Long.parseLong(by);
            this.prizeX = Long.parseLong(prizeX);
            this.prizeY = Long.parseLong(prizeY);
        }
    }

}
