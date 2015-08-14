package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by seal on 6/26/15.
 */
public class Reader {
    private BufferedReader reader;
    private List<List<String>> printList;
    private List<List<Integer>> printIntList;
    private List<List<Double>> printDoubleList;
    private String path;

    public Reader() {
    }

    public Reader(String path) {
        this.path = path;
        init();
    }

    private void init() {
        try {
            reader = new BufferedReader(new FileReader(this.path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> readFile() {
        init();
        List<List<String>> lists = new ArrayList<>();

        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(",");
                List<String> tempList = Arrays.asList(strings);
                lists.add(tempList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        printList = lists;
        return lists;
    }

    public List<List<String>> readFile(String path) {
        this.path = path;
        return readFile();
    }

    public List<List<Integer>> readFileAsInt() {
        readFile();
        List<List<Integer>> listsInt = new ArrayList<>();

        for (List<String> row : printList) {
            List<Integer> temp = new ArrayList<>();
            for (String str : row) {
                int strToInt = Integer.parseInt(str);
                temp.add(strToInt);
            }
            listsInt.add(temp);
        }
        printIntList = listsInt;
        return listsInt;
    }

    public List<List<Double>> readFileAsDouble() {
        readFile();
        List<List<Double>> listsInt = new ArrayList<>();

        for (List<String> row : printList) {
            List<Double> temp = new ArrayList<>();
            for (String str : row) {
                double strToInt = Double.parseDouble(str);
                temp.add(strToInt);
            }
            listsInt.add(temp);
        }
        printDoubleList = listsInt;
        return listsInt;
    }

    public List<List<Integer>> readFileAsInt(String path) {
        this.path = path;
        return readFileAsInt();
    }

    public List<List<Double>> readFileAsDouble(String path) {
        this.path = path;
        return readFileAsDouble();
    }

    public void printIntData() {
        if (printIntList == null) readFile();
        for (List<Integer> row : printIntList) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public void printDoubleData() {
        if (printDoubleList == null) readFile();
        for (List<Double> row : printDoubleList) {
            for (double i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public void printReadData() {
        for (List<String> row : printList) {
            for (String str : row) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
    }

}
