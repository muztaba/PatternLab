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
            List<Integer> continuousData = separateContinuousData(row);
            int classNumber = row.get(row.size() - 1);
            for (int index = 0; index < continuousDataColumn.length; index++) {
                int column = continuousDataColumn[index];
                if (!map.containsKey(column)) {
                    map.put(column, new Node());
                }
                map.get(column).add(classNumber, continuousData.get(index));
            }
        }
        super.train(lists);
        for (int i : continuousDataColumn) {
            map.get(i).calculateMean();
            map.get(i).calculateStd();
        }
        calculateTotalSum();
    }

    private List<Integer> separateContinuousData(List<Integer> row) {
        List<Integer> list = new ArrayList<>();
        for (int index = continuousDataColumn.length - 1; index >= 0; index--) {
            int column = continuousDataColumn[index];
            int feature = row.get(column);
            list.add(feature);
            row.remove(column);
        }
        Collections.reverse(list);
        return list;
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
        public double getStd(int classNumber) {
            return std.get(classNumber);
        }

    }

    @Override
    public void test(List<List<Integer>> lists) {
        PriorityQueue<NaiveClassifier.Node> queue = new PriorityQueue<>();
        Set<Integer> set = new TreeSet<>();
        {
            for (int i : continuousDataColumn) {
                set.add(i);
            }
        }

        for (List<Integer> row : lists) {

            List<Integer> continuousData = separateContinuousData(row);
            int originClass = row.get(row.size() - 1);
            for (int classNumber = 1; classNumber < super.classNumber; classNumber++) {
                double s = super.probTo(classNumber, row.subList(0, row.size() - 1));
                double p = 1.0;
                for (int colIndex = 0; colIndex < continuousDataColumn.length; colIndex++) {
                    p *= probContinuousData(continuousDataColumn[colIndex], classNumber, continuousData.get(colIndex));
                }
                queue.add(new NaiveClassifier.Node(classNumber, p * s));
            }

            int c = queue.peek().classNumber;

            if (c != originClass) {
                super.error++;
            }
        }

    }


    private double probContinuousData(int column, int classNumber, double feature) {
        double p = 1.0;
        double mean = map.get(column).getMean(classNumber);
        double std = map.get(column).getStd(classNumber);
        std = Math.pow(std, 2);
        double first = 1 / Math.sqrt(2 * Math.PI * std);
        double second = Math.exp(Math.pow((feature - mean), 2) / (2 * std));
        return first * second;
    }

}
