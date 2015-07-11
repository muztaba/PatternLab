package algorithm.perceptron.single;

import Utils.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by seal on 7/11/15.
 */
public class Train {
    public final int classIndex;

    private final String[] classSing;
    protected double[] wight;

    protected Random random = new Random();

    public Train( int classIndex, String... classSing) {
        this.classIndex = classIndex;
        this.classSing = classSing;
    }

    public void train(int iteration, List<List<Integer>> trainList) {
        this.wight = ArrayUtils.makeArray(7, -7, 7, random);
        for (int _iteration = 0; _iteration < iteration; ++_iteration) {
            int error = 0;
            int[] errorSum = ArrayUtils.makeArray(classIndex - 1, 0);

            for (List<Integer> row : trainList) {
                double gw = mul(row.subList(0, classIndex));
                if (gw > 0 && row.get(classIndex) != 1) {

                }
            }
        }
    }

    protected double mul(List<Integer> x) {
        double sum = 0.0;
        for (int i = 0; i < x.size(); i++) {
            sum += x.get(i) * wight[i];
        }
        return sum;
    }

}
