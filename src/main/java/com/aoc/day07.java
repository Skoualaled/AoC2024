package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day07 {

    private static final List<Operation> operationsFile = new ArrayList<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day07.in");
        readFile(input);
        part1(); // 1260333054159
        part2(); // 162042343638683
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            while(scan.hasNext()){
                String[] values = scan.nextLine().split(" ");
                long tot = Long.parseLong(values[0].replaceAll(":",""));
                ArrayList<Long> members = new ArrayList<>();
                for(int i = 1; i < values.length; i++){
                    members.add(Long.parseLong(values[i]));
                }
                Operation op = new Operation(tot, members);
                operationsFile.add(op);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1(){
        List<String> operands = List.of("+", "*");
        long res = calibrationValue(operands);
        System.out.println("Part 1 : " + res);
    }


    private static void part2() {
        List<String> operands = List.of("+", "*", "||");
        long res = calibrationValue(operands);
        System.out.println("Part 2 : " + res);
    }

    private static long calibrationValue(List<String> operands) {
        long res = 0;
        for (Operation op : day07.operationsFile){
            long fx = op.members.get(0);
            long fy = op.members.get(1);
            List<Long> rest = op.members.subList(2, op.members.size());
            List<Long> values = check(fx, fy, rest, operands);
            res += values.contains(op.total) ? op.total : 0;
        }
        return res;
    }

    private static List<Long> check(long x, long y, List<Long> suite, List<String> op){
        List<Long> res = new ArrayList<>();
        for(String o : op){
            long val = calc(x,y,o);
            if (suite.isEmpty()) res.add(val);
            else {
                List<Long> pop = new ArrayList<>(suite);
                pop.remove(0);
                res.addAll(check(val, suite.get(0), pop, op));
            }
        }
        return res;
    }

    private static long calc(long x, long y, String op){
        return switch (op) {
            case "+" -> x + y;
            case "*" -> x * y;
            case "||" -> Long.parseLong(x + "" + y);
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }

    public static class Operation{
        private final Long total;
        private final ArrayList<Long> members;

        public Operation(long t, ArrayList<Long> op){
            this.total = t;
            this.members = op;
        }

    }
}
