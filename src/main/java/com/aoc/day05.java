package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day05 {

    private static Map<String, Set<String>> rulesMap;
    private static List<List<String>> updates;

    public static void main(String[] args) {
        File input = new File("src/main/resources/day05.in");
        readFile(input);
        part1(); // 4135
        part2(); // 5285
    }

    private static void readFile(File file){
       // Scanner input = null;
        rulesMap = new HashMap<>();
        updates = new ArrayList<>();
        try {
            Scanner input = new Scanner(file);
            while(input.hasNext()){
                String line = input.nextLine();
                Matcher rule = Pattern.compile("\\d+\\|\\d+").matcher(line);
                Matcher update = Pattern.compile("\\d+(,\\d+)+").matcher(line);
                if (rule.find()){
                    String[] values = line.split("\\|");
                    if (rulesMap.containsKey(values[0])){
                        Set<String> newVals = rulesMap.get(values[0]);
                        newVals.add(values[1]);
                        rulesMap.replace(values[0], newVals);

                    }else {
                        Set<String> newVal = new HashSet<>();
                        newVal.add(values[1]);
                        rulesMap.put(values[0],newVal);
                    }
                }
                if (update.find()){
                    String[] values = line.split(",");
                    ArrayList<String> newUpdate = new ArrayList<>(Arrays.asList(values));
                    updates.add(newUpdate);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int validUpdate(List<String> update){
        for(int i = update.size()-1; i > 0; i--){
            List<String> prev = update.subList(0,i);
            Set<String> rule = rulesMap.get(update.get(i));
            if (rule != null) {
                for (String prevVal : prev) {
                    if (rule.contains(prevVal)) return 0;
                }
            }
        }
        return Integer.parseInt(update.get(update.size()/2));
    }

    private static void part1(){
        int sum = 0;
        for(List<String> s : updates){
            sum += validUpdate(s);
        }
        System.out.println("Part 1 : " + sum);
    }

    public static int ruleCompare(String s1, String s2){
        if(Objects.equals(s1, s2) || !rulesMap.containsKey(s1)) return 0;
        if (rulesMap.get(s1).contains(s2)) {
            return -1;
        } else {
            return 1;
        }
    }

    private static void part2(){
        int sum = 0;
        for (List<String> s : updates){
            if (validUpdate(s) > 0) continue;
            s.sort(day05::ruleCompare);
            sum += validUpdate(s);
        }
        System.out.println("Part 2 : " + sum);
    }
}
