package com.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class day09 {
    private static ArrayList<Block> DiskMap;
    private static int[] charMap;
    public static void main(String[] args) {
        File input = new File("src/main/resources/day09.in");
        part1(input); // 6398608069280
        part2(input); // 6427437134372
    }

    public static void readFile(File file){
        try {
            DiskMap = new ArrayList<>();
            Scanner input = new Scanner(file);
            String[] line = input.next().split("");
            int Ids = 0;
            for(int i = 0; i < line.length; i++){
                boolean free = i%2!=0;
                int val = Integer.parseInt(line[i]);
                int curID = 0;
                if (!free){
                    curID = Ids;
                    Ids++;
                }
                Block block = new Block(val, curID, free);
                DiskMap.add(block);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static Block getLastUsedBlock(){
        for (int i = DiskMap.size()-1; i > 0; i--){
            if (DiskMap.get(i).free) DiskMap.remove(i);
            else return DiskMap.get(i);
        }
        return new Block(0,-1,true);
    }
    private static int getFirstFreeBlockIdx(){
        for (int i = 0; i < DiskMap.size()-1; i++){
            if (DiskMap.get(i).free) return i;
        }
        return -1;
    }

    private static int getFirstFreeBlockIdxBySize(int size, int idx){
        for (int i = 0; i <= idx; i++){
            if (DiskMap.get(i).free && DiskMap.get(i).size >= size) return i;
        }
        return -1;
    }

    private static void printDisk(){
        StringBuilder disk = new StringBuilder();
        for (Block b: DiskMap){
            if (b.free) {
                disk.append(".".repeat(b.size));
            } else if (b.ID >= 10){
                disk.append((b.ID +"|").repeat(b.size-1)).append(b.ID);
            } else{
                disk.append(String.valueOf(b.ID).repeat(b.size));
            }
        }
        System.out.println(disk);
    }

    private static void swapBlockP1(Block lastBlock, Block firstFreeBlock, int firstFreeBlockIdx){
        if (lastBlock.size >= firstFreeBlock.size) {
            Block replacementBlock = new Block(firstFreeBlock.size, lastBlock.ID, false);
            Block replacementLasteBlock = new Block(lastBlock.size - firstFreeBlock.size, lastBlock.ID, false);
            DiskMap.set(DiskMap.size()-1, replacementLasteBlock);
            DiskMap.set(firstFreeBlockIdx, replacementBlock);
            //lastBlock = replacementLasteBlock;
        } else {
            firstFreeBlock.size -= lastBlock.size;
            Block replacementBlock = new Block(lastBlock.size, lastBlock.ID, false);
            DiskMap.add(firstFreeBlockIdx, replacementBlock);
            DiskMap.get(DiskMap.size()-1).free = true;
            //lastBlock = getLastUsedBlock();
        }
    }

    private static void defragDisk(){

        int firstFreeBlockIdx = getFirstFreeBlockIdx();
        while (firstFreeBlockIdx > 0) {
            Block firstFreeBlock = DiskMap.get(firstFreeBlockIdx);
            Block lastBlock = getLastUsedBlock();
            swapBlockP1(lastBlock, firstFreeBlock, firstFreeBlockIdx);
            firstFreeBlockIdx = getFirstFreeBlockIdx();
            //printDisk();
        }
    }

    private static long getCheckSum(){
        long sum = 0;
        int position = 0;
        for (Block b : DiskMap){
            if (!b.free) {
                for (int i = position; i < position + b.size; i++) {
                    sum += ((long) i * b.ID);
                }
            }
            position += b.size;
        }
        return sum;
    }

    private static void swapBlockP2(int usedIdx, int freeIdx) {
        Block insert = new Block(DiskMap.get(usedIdx));
        insert.moved = true;
        DiskMap.get(freeIdx).size -= insert.size;
        DiskMap.get(usedIdx).free = true;
        DiskMap.add(freeIdx, insert);
    }
    private static void compactDisk(){

        for(int idx = DiskMap.size()-1; idx > 0; idx--){
            //printDisk();
            Block lastBlock = DiskMap.get(idx);
            if (lastBlock.free || lastBlock.moved) continue;
            int freeIdx = getFirstFreeBlockIdxBySize(lastBlock.size, idx);
            if(freeIdx == -1 ) continue;
            swapBlockP2(idx, freeIdx);
        }
    }

    public static void part1(File input){
        readFile(input);
        defragDisk();
        System.out.println(getCheckSum());
       // fileByChar(input);
    }

    public static void part2(File input){
        readFile(input);
        compactDisk();
        System.out.println(getCheckSum());
    }

    private static class Block{
        private int size;
        private final int ID;
        private boolean free;
        private boolean moved;

        private Block(int  size, int ID, boolean free){
            this.size = size;
            this.ID = ID;
            this.free = free;
            this.moved = false;
        }

        private Block(Block b){
            this.size = b.size;
            this.ID = b.ID;
            this.free = b.free;
            this.moved = false;
        }

    }

}
