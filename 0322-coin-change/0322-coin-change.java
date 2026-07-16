import java.util.Arrays;

class Solution {
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;

        int[][] dp = new int[n][amount + 1];
        for (int[] i : dp) {
            Arrays.fill(i, -1);
        }

        int ans = f(n - 1, amount, coins, dp);

        return ans >= (int)1e9 ? -1 : ans;
    }
    private int f(int i, int amount, int[] coins, int[][] dp) {

        if (i == 0) {
            if (amount % coins[0] == 0)
                return amount / coins[0];
            return (int)1e9;
        }

        if (dp[i][amount] != -1)
            return dp[i][amount];

        int notPick = f(i - 1, amount, coins, dp);

        int pick = (int)1e9;
        if (coins[i] <= amount) {
            pick = 1 + f(i, amount - coins[i], coins, dp);
        }

        return dp[i][amount] = Math.min(pick, notPick);
    }
}