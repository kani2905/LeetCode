class Solution {
    public int characterReplacement(String s, int k) {
        int n = s.length();
        int l = 0;
        int max = 0;
        int ans = 0;
        int[] count = new int[26];

        for(int i =0;i<n;i++){
            count[s.charAt(i) - 'A']++;
            max = Math.max(max , count[s.charAt(i) - 'A']);

            while((i - l + 1) - max > k){
                count[s.charAt(l) - 'A']--;
                l++;
            }
            ans = Math.max(ans , i - l + 1);
        }
        return ans;
    }
}