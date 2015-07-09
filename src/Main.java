import algorithm.ContinuousFeature;
import algorithm.NaiveClassifier;
import io.Reader;

import java.util.List;

/**
 * Created by seal on 6/21/15.
 */
public class Main {
    public static void main(String[] args) {
        // Read from .csv file
        Reader reader = new Reader();
        List<List<Integer>> trainList = reader.readFileAsInt("inputFile/germanTrain.csv");
        List<List<Integer>> testData = reader.readFileAsInt("inputFile/germanTest.csv");

//        NaiveClassifier solver = new NaiveClassifier(2);
//        solver.train(trainList);
//        solver.test(testData);
//        System.out.println(solver.getError());

        ContinuousFeature solver = new ContinuousFeature(2, 7, 1);
        solver.train(trainList);
        solver.test(testData);
        System.out.println(solver.getError());
    }
}

