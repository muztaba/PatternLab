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

    public void train(int iteration, List<List> trainList) {
        this.wight = ArrayUtils.makeArray(7, -7, 7, random);
        for (int _iteration = 0; _iteration < iteration; ++_iteration) {
            int error = 0;
            int[] errorSum = ArrayUtils.makeArray(classIndex, 0);

            for (List<Integer> row : trainList) {
                row.add(1);

            }
        }
    }

    protected void mul(List<Integer> x, double[] w) {
        for (int i = 0; i < x.size(); i++) {
            w[i] = x.get(i) * w[i];
        }
    }

}
