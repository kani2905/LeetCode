import java.util.*;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {

        int n = triangle.size();

        int[][] dp = new int[n][n];

        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        return f(0, 0, triangle, dp);
    }

    static int f(int i, int j, List<List<Integer>> triangle, int[][] dp) {

        if(i == triangle.size() - 1){
            return triangle.get(i).get(j);
        }

        if(dp[i][j] != Integer.MAX_VALUE){
            return dp[i][j];
        }

        int down = triangle.get(i).get(j)
                 + f(i + 1, j, triangle, dp);

        int diagonal = triangle.get(i).get(j)
                     + f(i + 1, j + 1, triangle, dp);

        return dp[i][j] = Math.min(down, diagonal);
    }
}