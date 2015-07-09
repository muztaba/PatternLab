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

    public ContinuousFeature(int classNumber, int classIndex, int... continuousDataColumn) {
        super.classNumber = classNumber;
        this.classIndex = classIndex;
        Arrays.sort(continuousDataColumn);
    }

    @Override
    public void train(List<List<Integer>> lists) {

    }

    private class Node {

    }

    private void separateContinuousData(List<Integer> row) {
        int classNumber = row.get(classIndex);
        for (int index = classIndex - 1; index >= 0; index--) {

        }
    }

}
