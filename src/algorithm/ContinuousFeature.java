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
    }

    private class Node {
        // key = indicate class number;
        // value(list of integer) = feature belong to class.
        Map<Integer, List<Integer>> map = new HashMap<>();

        public void add(int classNumber, int feature) {
            if (!map.containsKey(classNumber)) {
                map.put(classNumber, new ArrayList<>());
            }
            map.get(classNumber).add(feature);
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
