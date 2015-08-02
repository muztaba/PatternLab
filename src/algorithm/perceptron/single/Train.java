package algorithm.perceptron.single;

import Utils.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by seal on 7/11/15.
 */
public class Train {

    protected double[] wight;
    public static final double P = .25;
    protected Random random = new Random();

    public Train( ) {
    }

    public void train(int iteration, List<List<Integer>> trainList) {
        int rowSize = trainList.get(0).size();
        this.wight = new double[rowSize];
        int classIndex = trainList.get(0).size() - 1;

        // init random value
        for (int i = 0; i < wight.length - 1; i++) {
            wight[i] = random.nextInt();
        }

        for (int _iteration = 0; _iteration < iteration; _iteration++) {
            double[] errorSum = new double[rowSize];
            int flag = 0;
            double gw = 0.0;
            for (List<Integer> row : trainList) {
                gw = mul(row.subList(0, classIndex));
                if (gw > 0 && row.get(classIndex) != 1) {
                    sum(errorSum, row.subList(0, classIndex));
                    flag++;
                } else if (gw < 0 && row.get(classIndex) != 2) {
                    sum(errorSum, row.subList(0, classIndex));
                }
            }
            if (flag == 0) break;
            // weight update
            for (int i = 0; i < wight.length; i++) {
                wight[i] = wight[i] - (P * errorSum[i]);
            }
        }
    }

    public void test(List<List<Integer>> trainList) {
        int classIndex = trainList.get(0).size() - 1;
        int error = 0;
        for (List<Integer> row : trainList) {
            double gw = 0.0;
            gw = mul(row.subList(0, classIndex));

            if (gw > 0 && row.get(classIndex) != 1) {
                error++;
            } else if (gw < 0 && row.get(classIndex) != 2) {
                error++;
            }
        }
        System.out.println(error);
    }

    protected void sum(double[] errorsum, List<Integer> list) {
        for (int i = 0; i < Math.min(errorsum.length, list.size()); i++) {
            errorsum[i] = errorsum[i] + list.get(i);
        }
        errorsum[errorsum.length - 1] = 1;
    }

    protected double mul(List<Integer> x) {
        double sum = 0.0;
        for (int i = 0; i < x.size(); i++) {
            sum += x.get(i) * wight[i];
        }
        return sum;
    }

}
