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
    private static final ArrayList<ClawMachine> machines = new ArrayList<>();

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
            ax = ay = bx = by = px = py = 0L;
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
        long tokens = 0L;
        for(ClawMachine m : machines){
            tokens += m.Solve();
        }
        System.out.println("Part 1 : " + tokens);
    }

    private static void part2() {
        long tokens = 0L;
        for(ClawMachine m : machines){
            m.prizeX += 10000000000000L;
            m.prizeY += 10000000000000L;
            tokens += m.Solve();
        }
        System.out.println("Part 2 : " + tokens);
    }

    private static class ClawMachine{
        final long ax;
        final long ay;
        final long bx;
        final long by;
        long prizeX;
        long prizeY;

        public ClawMachine(long ax, long ay, long bx,long by, long prizeX, long prizeY){
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.prizeX = prizeX;
            this.prizeY = prizeY;
        }

        public long Solve(){
            // calcul par matrice inversée
            double nbButtonA = (double) (by*prizeX - bx*prizeY)/ (ax*by - bx*ay);
            double nbButtonB = (double) (ax*prizeY - ay*prizeX) / (ax*by - bx*ay);
            if(nbButtonA % 1 == 0 && nbButtonB % 1 == 0) return (long) (3*nbButtonA+nbButtonB);
            else return 0;
        }
    }

}
