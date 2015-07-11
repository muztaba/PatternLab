package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by seal on 7/2/15.
 */
public class ArrayUtils {
    public static List<Integer> arrayToIntList(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i : array) list.add(i);
        return list;
    }


    public static int[] makeArray(int length, int fillVal) {
        int[] array = new int[length];
        Arrays.fill(array, fillVal);
        return array;
    }

    public static int[] makeArray(int length,int from, int to, int fillVal) {
        int[] array = new int[length];
        Arrays.fill(array, from, to, fillVal);
        return array;
    }



    public static int[] makeArray(int length,int lowerBound, int upperBound, Random random) {
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(Math.abs(lowerBound) + upperBound) - upperBound;
        }
        return array;
    }

    public static int[] makeArray(int length,int from, int to,
                                  int lowerBound, int upperBound, Random random) {
        int[] array = new int[length];
        for (int i = from; i < to; i++) {
            array[i] = random.nextInt(Math.abs(lowerBound) + upperBound) - upperBound;
        }
        return array;
    }


}
