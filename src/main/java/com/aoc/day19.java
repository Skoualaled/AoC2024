package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day19 {

    private static List<String> patterns = new ArrayList<>();
    private static final ArrayList<String> designs = new ArrayList<>();
    private static final Map<String, Long> designPossibilities = new HashMap<>();

    public static void main(String[] args) {
        File input = new File("src/main/resources/day19.in");
        readFile(input);
        part1();
        part2();
    }

    private static void readFile(File input) {
        try {
            Scanner scan = new Scanner(input);
            patterns = Arrays.stream(scan.nextLine().split(", ")).toList();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!line.isBlank()) designs.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1() {
        int possibleDesigns = 0;
        StringBuilder towelRegex = new StringBuilder();
        for (String towel : patterns) {
            towelRegex.append(towel).append("|");
        }
        String reg = "^(" + towelRegex.subSequence(0, towelRegex.length() - 1) + ")*$";
        for (String d : designs) {
            if (d.matches(reg)) possibleDesigns++;
        }
        System.out.println("Part 1 : " + possibleDesigns);
    }

    private static void part2() {
        long AllPossibleDesign = designs.stream().mapToLong(day19::countPossibleDesigns).sum();
        System.out.println("Part 2 : " + AllPossibleDesign);
    }

    private static long countPossibleDesigns(String design) {
        if (designPossibilities.containsKey(design)) return designPossibilities.get(design);
        long res = 0L;
        for (String p : patterns) {
            if (design.equals(p)) res++;
            else if (design.startsWith(p)) res += countPossibleDesigns(design.substring(p.length()));
        }
        designPossibilities.put(design, res);
        return res;
    }
}
