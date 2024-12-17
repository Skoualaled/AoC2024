package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class day17 {

    private static int[] program;
    private static long RegisterA;
    private static long RegisterB;
    private static long RegisterC;
    private static int pointer = 0;
    private static final StringBuilder out = new StringBuilder();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day17.in");
        readFile(input);
        part1();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            while(scan.hasNextLine()) {
                String line  = scan.nextLine();
                if(line.matches("Register A: \\d*")) {
                    RegisterA = Integer.parseInt(line.replaceAll("\\D", ""));
                } else if (line.matches("Register B: \\d*")) {
                    RegisterB = Long.parseLong(line.replaceAll("\\D", ""));
                } else if (line.matches("Register C: \\d*")) {
                    RegisterC = Long.parseLong(line.replaceAll("\\D", ""));
                } else if (!line.isBlank()) {
                    program = Arrays.stream(line.replace("Program: ", "").split(",")).mapToInt(Integer::parseInt).toArray();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part1(){
        while(pointer < program.length){
            instruct(program[pointer], program[pointer+1]);
            pointer +=2;
        }
        System.out.println("Part 1 : " + out.substring(0, out.length()-1));
    }

    public static long comboOperand(long val){
        if (val == 4) return RegisterA;
        else if (val == 5) return RegisterB;
        else if (val == 6) return RegisterC;
        else return val;
    }

    public static long power(long val, long pow){
        if(pow == 0) return 1;
        long res = val;
        while (pow > 1){
            res *= val;
            pow--;
        }
        return res;
    }

    public static void instruct(int opcode, int val){
        if(opcode == 0) RegisterA = Math.floorDiv(RegisterA, power(2,comboOperand(val)));
        else if (opcode == 1) RegisterB = RegisterB^val;
        else if (opcode == 2) RegisterB = comboOperand(val)%8;
        else if (opcode == 3 && RegisterA != 0) pointer = val-2;
        else if (opcode == 4) RegisterB = RegisterB^RegisterC;
        else if (opcode == 5) out.append(comboOperand(val)%8).append(',');
        else if (opcode == 6) RegisterB = Math.floorDiv(RegisterA, power(2,comboOperand(val)));
        else if (opcode == 7) RegisterC = Math.floorDiv(RegisterA, power(2,comboOperand(val)));
    }
}
