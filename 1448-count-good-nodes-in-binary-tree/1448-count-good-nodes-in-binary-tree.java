/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int goodNodes(TreeNode root) {
        return f(root, root.val);
    }

    static int f(TreeNode root, int max) {
        if (root == null) {
            return 0;
        }

        TreeNode curr = root;
        int count = 0;

        if (curr.val >= max) {
            count++;
            max = curr.val;
        }

        int left = f(root.left, max);
        int right = f(root.right, max);

        return count + left + right;
    }
}