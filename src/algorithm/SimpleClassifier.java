package algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seal on 6/27/15.
 */
public class SimpleClassifier {
    private Map<String, Integer> class1, class2;
    private int error;

    public SimpleClassifier() {
        class1 = new HashMap<>();
        class2 = new HashMap<>();
    }

    public void training(List<List<String>> lists) {
        for (List<String> row : lists) {
            int classIndex = row.size() - 1;
            String str = toString(row, classIndex);
            if (row.get(classIndex).equals("1")) insertToMap(str, this.class1);
            else insertToMap(str, this.class2);
        }
    }

    public void clarification(List<List<String>> lists) {
        for (List<String> row : lists) {
            int classIndex = row.size() - 1;
            int sum = 0;
            String str = toString(row, classIndex);

            sum += getIntFromMap(str, this.class1);
            sum += getIntFromMap(str, this.class2);

            if (sum == 0) {
                throw new IllegalArgumentException("This is the moment, i was talking about :D");
            }

            double one = getIntFromMap(str, this.class1) / sum;
            double two = getIntFromMap(str, this.class2) / sum;

            int c = (one > two) ? 1 : 2;

            if (!row.get(classIndex).equals(Integer.toString(c))) error++;
        }
    }

    public int getError() {return this.error;}

    private int getIntFromMap(String str, Map<String, Integer> map) {
        return (map.containsKey(str)) ? map.get(str) : 0;
    }

    private void insertToMap(String str, Map<String, Integer> map) {
        if (map.containsKey(str)) map.put(str, map.get(str) + 1);
        else map.put(str, 1);
    }

    private String toString(List<String> list, int upTo) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < upTo; i++)
            builder.append(list.get(i));
        return builder.toString();
    }
}
