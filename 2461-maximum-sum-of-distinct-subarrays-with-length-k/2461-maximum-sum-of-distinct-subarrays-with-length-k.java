class Solution {
    public long maximumSubarraySum(int[] nums, int k) {
        int n = nums.length;
        int l = 0;
        long sum =0;
        long max = 0;
        Map<Integer , Integer> map = new HashMap<>();

        for(int i =0;i<n;i++){
            sum += nums[i];
            map.put(nums[i] , map.getOrDefault(nums[i] , 0) + 1);
            while(i - l + 1 > k){
                map.put(nums[l] , map.get(nums[l])-1);
                if(map.get(nums[l]) == 0){
                    map.remove(nums[l]);
                }
                sum -= nums[l];
                l++;
            }
            if(i - l + 1 == k && map.size() == k)
                max = Math.max(max , sum);
        }
        return max;
    }
}