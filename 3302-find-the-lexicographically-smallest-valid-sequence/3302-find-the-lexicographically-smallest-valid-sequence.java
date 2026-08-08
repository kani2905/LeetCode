class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        int[] suffix = new int[n + 1];
        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                suffix[i]++;
                j--;
            }
        }

        int[] ans = new int[m];
        int p = 0;
        boolean mismatchUsed = false;

        for (int i = 0; i < m; i++) {
            boolean found = false;

            while (p < n) {
                if (word1.charAt(p) == word2.charAt(i)) {
                    ans[i] = p++;
                    found = true;
                    break;
                }

                if (!mismatchUsed && suffix[p + 1] >= m - i - 1) {
                    ans[i] = p++;
                    mismatchUsed = true;
                    found = true;
                    break;
                }

                p++;
            }

            if (!found) {
                return new int[0];
            }
        }

        return ans;
    }
}