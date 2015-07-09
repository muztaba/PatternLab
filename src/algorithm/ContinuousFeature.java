package algorithm;

import Utils.ArrayUtils;

import java.util.*;
import java.util.jar.Pack200;

/**
 * Created by seal on 7/2/15.
 */
public class ContinuousFeature extends NaiveClassifier {
    // classNumber index.
    private final int classIndex;
    private int[] continuousDataColumn;
    private Map<Integer, Node> map = new HashMap<>();

    public ContinuousFeature(int classNumber, int classIndex, int... continuousDataColumn) {
        super(classNumber);
        this.classIndex = classIndex;
        Arrays.sort(continuousDataColumn);
        this.continuousDataColumn = continuousDataColumn;

    }

    @Override
    public void train(List<List<Integer>> lists) {
        for (List<Integer> row : lists) {
            separateContinuousData(row);
        }
        super.train(lists);
        for (int i : continuousDataColumn) {
            map.get(i).calculateMean();
            map.get(i).calculateStd();
        }
    }

    private class Node {
        // key = indicate class number;
        // value(list of integer) = feature belong to class.
        Map<Integer, List<Integer>> _map = new HashMap<>();
        Map<Integer, Double> mean = new HashMap<>();
        Map<Integer, Double> std = new HashMap<>();
        public void add(int classNumber, int feature) {
            if (!_map.containsKey(classNumber)) {
                _map.put(classNumber, new ArrayList<>());
            }
            _map.get(classNumber).add(feature);
        }

        public void calculateMean() {
            for (Map.Entry<Integer, List<Integer>> itr : _map.entrySet()) {
                double sum = 0.0;
                int classNumber = itr.getKey();
                for (int feature : itr.getValue()) {
                    sum += feature;
                }
                double mean = sum / itr.getValue().size();
                this.mean.put(classNumber, mean);
            }
        }

        public double getMean(int classNumber) {
            return mean.get(classNumber);
        }

        public void calculateStd() {
            for (Map.Entry<Integer, List<Integer>> itr : _map.entrySet()) {
                int classNumber = itr.getKey();
                double mean = getMean(classNumber); // Mean should be calculate first
                double temp = 0.0;
                for (int i : itr.getValue()) {
                    temp += Math.pow((i - mean), 2);
                }
                double s = temp / itr.getValue().size();
                s = Math.sqrt(s);
                std.put(classNumber, s);
            }
        }
    }

    private void separateContinuousData(List<Integer> row) {
        int classNumber = row.get(classIndex);
        for (int index = continuousDataColumn.length - 1; index >= 0; index--) {
            int column = continuousDataColumn[index];
            int feature = row.get(column);
            if (!map.containsKey(column)) {
                map.put(column, new Node());
            }
            map.get(column).add(classNumber, feature);
            row.remove(column);
        }
    }

    public void printTrainData(List<List<Integer>> lists) {
        for (List<Integer> row : lists) {
            for (int i : row ) System.out.print(i + " ");
            System.out.println();
        }
    }

}
