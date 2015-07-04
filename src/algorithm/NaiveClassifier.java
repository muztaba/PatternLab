package algorithm;

import java.util.*;

/**
 * Created by seal on 6/28/15.
 */
public class NaiveClassifier {

    // Map 1st = which class it is
    // Map 2nd = what feature it is. Generally column number
    // Map 3rd = what is the feature number. That is what is in a[i][j].
    private Map<Integer, Map<Integer, Map<Integer, Integer>>> classList;
    private Map<Integer, Integer> sumList;
    protected int classNumber;
    protected int error;
    private double totalSum;

    public NaiveClassifier() {
        throw new IllegalArgumentException("Class number should be initialized");
    }

    public NaiveClassifier(int classNumber) {
        this.classNumber = classNumber;
        sumList = new HashMap<>();
        classList = new HashMap<>();
        for (int i = 1; i <= this.classNumber; i++) {
            classList.put(i, new HashMap<>());
        }
    }

    public void train(List<List<Integer>> lists) {
        int classIndex = lists.get(0).size() - 1;
        for (List<Integer> row : lists) {
            int whichClass = row.get(classIndex);

            if (!sumList.containsKey(whichClass)) sumList.put(whichClass, 0);

            for (int featureNum = 0; featureNum < classIndex; featureNum++) {
                Map<Integer, Map<Integer, Integer>> temp = classList.get(whichClass);
                int feature = row.get(featureNum);

                if (!temp.containsKey(featureNum)) {
                    temp.put(featureNum, new HashMap<>());
                }

                if (!temp.get(featureNum).containsKey(feature)) {
                    temp.get(featureNum).put(feature, 1);
                } else {
                    temp.get(featureNum).put(feature, temp.get(featureNum).get(feature) + 1);
                }
                classList.put(whichClass, temp);
                sumList.put(whichClass, sumList.get(whichClass) + 1);
            }
        }
    }

    protected void calculateTotalSum() {
        for (Map.Entry<Integer, Integer> itr : sumList.entrySet()) {
            totalSum += itr.getValue().doubleValue();
        }
    }

    protected class Node implements Comparable<Node> {
        final int classNumber;
        final double probability;

        public Node(int classNumber, double probability) {
            this.classNumber = classNumber;
            this.probability = probability;
        }

        @Override
        public int compareTo(Node node) {
            return Double.compare(node.probability, this.probability);
        }

    }

    public void test(List<List<Integer>> testList) {
        calculateTotalSum();
        int classIndex = testList.get(0).size() - 1;
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (List<Integer> row : testList) {

            for (int classNum = 1; classNum <= this.classNumber; classNum++) {
                double probToClass = probTo(classNum, row.subList(0, classIndex));
                queue.add(new Node(classNum, probToClass));
            }

            int c = queue.peek().classNumber;
            if (row.get(classIndex) != c) error++;

            queue.clear();
        }
    }

    public int getError() {
        return this.error;
    }

    protected double probTo(int classNum, List<Integer> features) {
        double p = 1.0;
        for (int featureNum = 0; featureNum < features.size(); featureNum++) {
            double value = getFromMap(classNum, featureNum, features.get(featureNum));
            p *= value / sumList.get(classNum);
        }
        p *= sumList.get(classNum) / totalSum;
        return p;
    }

    protected double getFromMap(int classNum, int featureNum, int feature) {
        Integer integer = classList.get(classNum).get(featureNum).get(feature);
        return integer != null ? integer.doubleValue() : 1.0;
    }

    public void printSumList() {
        for (Map.Entry<Integer, Integer> itr : sumList.entrySet()) {
            System.out.println(itr.getKey() + " " + itr.getValue());
        }
    }

    public void printTrainData() {
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>> tempClassList : classList.entrySet()) {
            System.out.println(tempClassList.getKey());
            Map<Integer, Map<Integer, Integer>> tempFeature = tempClassList.getValue();
            for (Map.Entry<Integer, Map<Integer, Integer>> feature : tempFeature.entrySet()) {
                System.out.print(feature.getKey() + " -> ");
                Map<Integer, Integer> count = feature.getValue();
                for (Map.Entry<Integer, Integer> itr : count.entrySet()) {
                    System.out.println(itr.getKey() + " " + itr.getValue() + " ");
                }
            }
        }
    }

}
