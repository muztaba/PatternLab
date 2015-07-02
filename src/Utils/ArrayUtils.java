package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seal on 7/2/15.
 */
public class ArrayUtils {
    public static List<Integer> arrayToIntList(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i : array) list.add(i);
        return list;
    }
}
