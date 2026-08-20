import java.util.*;

class Solution {

    public static boolean isPossible(int[] bloomDays, int day, int m, int k) {
        int count = 0; 
        int bouquets = 0; 
        for (int bloom : bloomDays) {
            if (bloom <= day) {
                count++; 
                if (count == k) {
                    bouquets++; 
                    count = 0; 
                }
            } else {
                count = 0; 
            }
        }

        return bouquets >= m; 
    }

    public static int minDays(int[] bloomDays, int k, int m) {
        long required = (long) m * k;
        if (required > bloomDays.length) return -1; 
        int minDay = Integer.MAX_VALUE;
        int maxDay = Integer.MIN_VALUE;

        for (int bloom : bloomDays) {
            minDay = Math.min(minDay, bloom);
            maxDay = Math.max(maxDay, bloom);
        }

        int low = minDay, high = maxDay, result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (isPossible(bloomDays, mid, m, k)) {
                result = mid; 
                high = mid - 1;
            } else {
                low = mid + 1; 
            }
        }

        return result;
    }
}