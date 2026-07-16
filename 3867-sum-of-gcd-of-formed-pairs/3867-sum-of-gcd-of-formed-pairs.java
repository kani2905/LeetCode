import java.util.*;

class Solution {
    public long gcdSum(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];

        int max = Integer.MIN_VALUE;

        
        for (int i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);      
            prefix[i] = gcd(nums[i], max);     
        }

      
        Arrays.sort(prefix);

        long sum = 0;   
        int l = 0;
        int r = n - 1;

        
        while (l < r) {
            sum += gcd(prefix[l], prefix[r]);  
            l++;
            r--;
        }

        return sum;
    }

  
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}