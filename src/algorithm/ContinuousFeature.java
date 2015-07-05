package algorithm;

import Utils.ArrayUtils;

import java.util.*;

/**
 * Created by seal on 7/2/15.
 */
public class ContinuousFeature extends NaiveClassifier {
    List<Integer> continuousDataColumn;
    //    List<ContinuousData> continuousDataList;
    Map<Integer, ContinuousData> continuousDataList;

    public ContinuousFeature(int classNumber, int... continuousDataColumn) {
        super(classNumber);
        this.continuousDataColumn = ArrayUtils.arrayToIntList(continuousDataColumn);
        this.continuousDataList = new HashMap<>();

        for (int i : this.continuousDataColumn) {
            continuousDataList.put(i, new ContinuousData(i));
        }

    }

    @Override
    public void train(List<List<Integer>> testLists) {
        separateContinuousData(testLists);
        super.train(testLists);
        for (Map.Entry<Integer, ContinuousData> itr : continuousDataList.entrySet()) {
            itr.getValue().calculateMean();
            itr.getValue().calculateDeviation();
        }
    }

    public void printTest() {
        for (Map.Entry<Integer, ContinuousData> itr : continuousDataList.entrySet()) {
            itr.getValue().print();
            System.out.println("HI");
        }
    }

    private class ContinuousData {
        int classNumber = -1;
        Map<Integer, List<Integer>> dataMap;
        Map<Integer, Double> mean;
        Map<Integer, Double> deviation;

        public ContinuousData(final int classNumber) {
            this.classNumber = classNumber;
            mean = new HashMap<>();
            deviation = new HashMap<>();
            dataMap = new HashMap<>();
        }

        public void setData(int whichClass, int data) {
            if (!dataMap.containsKey(whichClass)) {
                dataMap.put(whichClass, new ArrayList<>());
            }
            dataMap.get(whichClass).add(data);
        }

        public double getMean(int whichClass) {
            return mean.get(whichClass);
        }

        public void calculateMean() {
            for (Map.Entry<Integer, List<Integer>> itr : dataMap.entrySet()) {
                List<Integer> list = itr.getValue();
                mean.put(itr.getKey(), mean(list));
            }
        }

        private double mean(List<Integer> list) {
            double sum = 0.0;
            for (int i : list) sum += i;
            return sum / list.size();
        }

        public void calculateDeviation() {
            for (Map.Entry<Integer, Double> itr : mean.entrySet()) {
                List<Integer> list = dataMap.get(itr.getKey());
                double deviationValue = 0.0;
                for (int i : list) {
                    deviationValue += Math.pow(mean.get(itr.getKey()) - i, 2);
                }
                deviation.put(itr.getKey(), deviationValue);
            }
        }

        public void print() {
            for (Map.Entry<Integer, Double> itr : mean.entrySet()) {
                System.out.println(itr.getValue());
            }
            for (Map.Entry<Integer, Double> itr : deviation.entrySet()) {
                System.out.println(itr.getValue());
            }
        }

        public double getStd(int classNumber) {
            return deviation.get(classNumber);
        }
    }

    private void separateContinuousData(List<List<Integer>> lists) {
        int classIndex = lists.get(0).size() - 1;
        for (List<Integer> row : lists) {
            for (int i = continuousDataColumn.size() - 1; i >= 0; i--) {
                int column = continuousDataColumn.get(i);
                int whichClass = row.get(classIndex);
                int data = row.get(column);
                setToDataMap(column, whichClass, data);
                row.remove(column);
            }
        }
    }

    private void setToDataMap(int column, int whichClass, int data) {
        continuousDataList.get(column).setData(whichClass, data);
    }

    @Override
    public void test(List<List<Integer>> testList) {
        super.calculateTotalSum();
        int classIndex = testList.get(0).size() - 1;
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (List<Integer> row : testList) {
            for (int classNum = 1; classNum <= this.classNumber; classNum++) {
                double probToClass = probTo(classNum, row.subList(0, classIndex));
                queue.add(new Node(classNum, probToClass));
            }

            int c = queue.peek().classNumber;
            if (row.get(classIndex) != c) error++;
        }

    }

    protected double probTo(int classNumber, List<Integer> list) {
        double p = 1.0;
        for (int column = 0; column < list.size(); column++) {
            if (continuousDataColumn.contains(column)) {
                p *= probToContinuousData(getMean(column, classNumber), getStd(column, classNumber));
            } else {
                double value = super.getFromMap(classNumber, column, list.get(column));
                p *= value / sumList.get(classNumber);
            }
        }
        p *= sumList.get(classNumber) / totalSum;
        return p;
    }

    protected double getMean(int column, int classNumber) {
        return continuousDataList.get(column).getMean(classNumber);
    }

    protected double getStd(int column, int classNumber) {
        return continuousDataList.get(column).getStd(classNumber);
    }

    protected double probToContinuousData(double mean, double std) {
        return 1.0;
    }
}
