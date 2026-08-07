import java.util.*;

public class Solution {
    private static final int[] PRIMES = {2, 3, 5, 7};
    private static final int[][] DIGIT_FACTORS = new int[10][4];

    static {
        // Precompute prime factor counts for digits 1-9
        for (int i = 1; i <= 9; i++) {
            int v = i;
            for (int j = 0; j < 4; j++) {
                int p = PRIMES[j];
                while (v % p == 0) {
                    DIGIT_FACTORS[i][j]++;
                    v /= p;
                }
            }
        }
    }

    public String smallestNumber(String num, long t) {
        // Step 1: Extract prime factors of t
        int[] targetCounts = new int[4];
        long temp = t;
        for (int j = 0; j < 4; j++) {
            int p = PRIMES[j];
            while (temp % p == 0) {
                targetCounts[j]++;
                temp /= p;
            }
        }

        // If t contains any prime factor other than 2, 3, 5, 7, it's impossible
        if (temp > 1) {
            return "-1";
        }

        int n = num.length();
        
        // Find the absolute minimum digit requirements for t
        Result minResult = getMinDigits(targetCounts[0], targetCounts[1], targetCounts[2], targetCounts[3]);
        if (minResult.length > n) {
            return minResult.digitString;
        }

        // Step 2: Check if the current num is already valid
        boolean zeroFree = true;
        int[] currentCounts = new int[4];
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            if (d == 0) {
                zeroFree = false;
                break;
            }
            for (int j = 0; j < 4; j++) {
                currentCounts[j] += DIGIT_FACTORS[d][j];
            }
        }

        if (zeroFree) {
            boolean fits = true;
            for (int j = 0; j < 4; j++) {
                if (currentCounts[j] < targetCounts[j]) {
                    fits = false;
                    break;
                }
            }
            if (fits) return num;
        }

        // Find the first occurrence of zero to optimize the search boundary
        int firstZero = num.indexOf('0');
        if (firstZero == -1) {
            firstZero = n;
        }

        // Step 3: Precompute prefix totals for prime factors
        int[][] prefCounts = new int[n + 1][4];
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            System.arraycopy(prefCounts[i], 0, prefCounts[i + 1], 0, 4);
            if (d > 0) {
                for (int j = 0; j < 4; j++) {
                    prefCounts[i + 1][j] += DIGIT_FACTORS[d][j];
                }
            }
        }

        // Step 4: Scan from right to left to find a mutation pivot point
        for (int i = n - 1; i >= 0; i--) {
            if (i > firstZero) {
                continue;
            }

            int currDigit = num.charAt(i) - '0';
            for (int bigger = currDigit + 1; bigger <= 9; bigger++) {
                int rem2 = targetCounts[0] - prefCounts[i][0] - DIGIT_FACTORS[bigger][0];
                int rem3 = targetCounts[1] - prefCounts[i][1] - DIGIT_FACTORS[bigger][1];
                int rem5 = targetCounts[2] - prefCounts[i][2] - DIGIT_FACTORS[bigger][2];
                int rem7 = targetCounts[3] - prefCounts[i][3] - DIGIT_FACTORS[bigger][3];

                Result remResult = getMinDigits(rem2, rem3, rem5, rem7);
                int spaceAvailable = n - 1 - i;

                if (remResult.length <= spaceAvailable) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(bigger);
                    
                    int onesNeeded = spaceAvailable - remResult.length;
                    for (int o = 0; o < onesNeeded; o++) {
                        sb.append('1');
                    }
                    sb.append(remResult.digitString);
                    return sb.toString();
                }
            }
        }

        // Step 5: If no combination fits inside length `n`, expand length to `n + 1`
        int targetLen = n + 1;
        StringBuilder sb = new StringBuilder();
        int onesNeeded = targetLen - minResult.length;
        for (int i = 0; i < onesNeeded; i++) {
            sb.append('1');
        }
        sb.append(minResult.digitString);
        return sb.toString();
    }

    // Pair and combine prime factors greedily into valid single digits
    private Result getMinDigits(int c2, int c3, int c5, int c7) {
        c2 = Math.max(0, c2);
        c3 = Math.max(0, c3);
        int n5 = Math.max(0, c5);
        int n7 = Math.max(0, c7);

        int bestTotal = Integer.MAX_VALUE;
        String bestStr = "";

        // Greedily bundle leftover 2s and 3s using digit 6
        int limit = Math.min(c2, c3);
        for (int n6 = 0; n6 <= limit; n6++) {
            int r2 = c2 - n6;
            int r3 = c3 - n6;

            int n9 = r3 / 2;
            int n3 = r3 % 2;

            int n8 = r2 / 3;
            int rem2 = r2 % 3;
            int n4 = 0, n2 = 0;
            if (rem2 == 1) {
                n2 = 1;
            } else if (rem2 == 2) {
                n4 = 1;
            }

            int total = n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9;
            if (total < bestTotal) {
                bestTotal = total;
                // Build lexically smallest string representation (sorted ascending)
                bestStr = buildString(n2, n3, n4, n5, n6, n7, n8, n9);
            } else if (total == bestTotal) {
                String currStr = buildString(n2, n3, n4, n5, n6, n7, n8, n9);
                if (currStr.compareTo(bestStr) < 0) {
                    bestStr = currStr;
                }
            }
        }
        return new Result(bestTotal, bestStr);
    }

    private String buildString(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        StringBuilder sb = new StringBuilder();
        appendChars(sb, '2', n2);
        appendChars(sb, '3', n3);
        appendChars(sb, '4', n4);
        appendChars(sb, '5', n5);
        appendChars(sb, '6', n6);
        appendChars(sb, '7', n7);
        appendChars(sb, '8', n8);
        appendChars(sb, '9', n9);
        return sb.toString();
    }

    private void appendChars(StringBuilder sb, char c, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
    }

    private static class Result {
        int length;
        String digitString;

        Result(int length, String digitString) {
            this.length = length;
            this.digitString = digitString;
        }
    }
}
