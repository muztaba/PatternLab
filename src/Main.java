import algorithm.ContinuousFeature;
import algorithm.NaiveClassifier;
import algorithm.perceptron.single.Train;
import io.Reader;

import java.util.List;

/**
 * Created by seal on 6/21/15.
 */
public class Main {
    public static void main(String[] args) {
        // Read from .csv file
        Reader reader = new Reader();

//        List<List<Integer>> trainList = reader.readFileAsInt("inputFile/iris_train.csv");
        List<List<Double>> trainData = reader.readFileAsDouble("inputFile/iris_train.csv");
        List<List<Double>> testData = reader.readFileAsDouble("inputFile/iris_test.csv");
//        List<List<Double>> trainList = reader.readFileAsDouble("inputFile/iris_train.csv");



        ContinuousFeature solver = new ContinuousFeature(3, 4, 0, 1, 2, 3);
        solver.train(trainData);
        solver.test(testData);
        System.out.println(solver.getError());
//        Train solver = new Train();
//        solver.train(1000, trainList);
//        solver.test(testData);
    }
}

