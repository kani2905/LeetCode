class Solution {

    public int minDays(int[] bloomDay, int m, int k) {

        int n = bloomDay.length;
        if ((long) m * k > n) {
            return -1;
        }

        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;

        for (int day : bloomDay) {
            low = Math.min(low, day);
            high = Math.max(high, day);
        }

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (canMake(bloomDay, m, k, mid)) {
                high = mid;       
            } else {
                low = mid + 1;    
            }
        }

        return low;
    }

    private boolean canMake(int[] bloomDay, int m, int k, int days) {

        int flowers = 0;
        int bouquets = 0;

        for (int day : bloomDay) {

            if (day <= days) {
                flowers++;

                if (flowers == k) {
                    bouquets++;
                    flowers = 0;
                }

            } else {
                flowers = 0;
            }

            if (bouquets >= m) {
                return true;
            }
        }

        return false;
    }
}