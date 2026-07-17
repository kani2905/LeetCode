class Solution {
    public int candy(int[] ratings) {
        int[] dp = new int[ratings.length];
        Arrays.fill(dp , 1);

        for(int i = 1 ;i<ratings.length;i++){
            if(ratings[i] > ratings[i-1]){
                dp[i] = 1 + dp[i-1];
            }
        }
        for(int i = ratings.length - 2;i>=0;i--){
            if(ratings[i] > ratings[i+1]){
                dp[i] = Math.max(dp[i] , dp[i+1]+1);
            }
        }
        int tot = 0;
        for(int i : dp){
            tot += i;
        }
        return tot;
    }
}