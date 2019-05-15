package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        double low = oomages.size() / 50.0;
        double high = oomages.size() / 2.5;

        int[] buckets = new int[M];

        for (Oomage o : oomages) {
            /* 0111 1111 1111 1111 1111 1111 1111 1111 */
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            int a = 2147483648 & 0x7FFFFFFF;
            // System.out.println(bucketNum);
            buckets[bucketNum] += 1;
        }

        for (int num : buckets) {
            if (num < low || num > high) {
                return false;
            }
        }

        return true;
    }
}
