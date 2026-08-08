import java.util.Arrays;

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i], -1);
        }

        return f(m - 1, n - 1, dp, obstacleGrid);
    }

    static int f(int i, int j, int[][] dp, int[][] matrix) {

       if(i < 0 || j < 0){
            return 0;
        }
        if(matrix[i][j] == 1){
            return 0;
        }
     
        if(i == 0 && j == 0){
            return 1;
        }
       
        if(dp[i][j] != -1){
            return dp[i][j];
        }

        int up = f(i - 1, j, dp, matrix);
        int left = f(i, j - 1, dp, matrix);

        return dp[i][j] = up + left;
    }
}