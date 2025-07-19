package coding.tixi.day28;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzherong
 * @date 2025/6/28 19:51
 */
public class Leetcode572 {

     public static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
         return findIndex(preSerial(root), preSerial(subRoot)) != -1;
    }

    public String preSerial(TreeNode node) {
         if (node == null) {
             return "#";
         }
         return "#" + node.val + "#" + preSerial(node.left) + "#" + preSerial(node.right) + "#";
    }

    public int findIndex(String s1, String s2) {
         int L1 = 0;
         int L2 = 0;
        int[] next = getNext(s2);
        while (L1 < s1.length() && L2 < s2.length()) {
             if (s1.charAt(L1) == s2.charAt(L2)) {
                 L1++;
                 L2++;
             } else if (next[L2] >= 0) {
                 L2 = next[L2];
             } else {
                 L1++;
             }
         }
        return L2 == s2.length()? L1 - s2.length() : -1;
    }
    public int[] getNext(String str) {
         char[] array = str.toCharArray();
         if (str.length() == 1) {
             return new int[]{-1};
         }
         int[] next = new int[str.length()];
         next[0] = -1;
         next[1] = 0;
         int index = 2;
         int pre = 0;
         while (index < str.length()) {
             if (array[index-1] == array[pre]) {
                 next[index++] = ++pre;
             } else if (next[pre] >= 0) {
                 pre = next[pre];
             } else {
                 next[index++] = 0;
             }
         }
         return next;
    }


    public boolean isSubtree2(TreeNode root, TreeNode subRoot) {
        return getIndex1(preSerial1(root), preSerial1(subRoot)) != -1;
    }

    public String[] preSerial1(TreeNode node) {
         List<String> ans = new ArrayList<>();
         pres(node, ans);
         return ans.toArray(new String[0]);
    }

    public void pres(TreeNode node, List<String> ans) {
         if (node == null) {
             ans.add("null");
         } else {
             ans.add(String.valueOf(node.val));
             pres(node.left, ans);
             pres(node.right, ans);
         }
    }

    public int getIndex1(String[] s1, String[] s2) {
         int L1 = 0;
         int L2 = 0;
        int[] next = getNext1(s2);
        while (L1 < s1.length && L2 < s2.length) {
             if (s1[L1].equals(s2[L2])) {
                 L1++;
                 L2++;
             } else if (next[L2] >= 0) {
                 L2 = next[L2];
             } else {
                 L1++;
             }
         }
         return L2 == s2.length? L1 - s2.length : -1;
    }

    public int[] getNext1(String[] s) {
         if (s == null || s.length == 0) {
             return new int[]{-1};
         }
         int[] next = new int[s.length];
         next[0] = -1;
         next[1] = 0;
         int index = 2;
         int pre = 0;
         while (index < s.length) {
             if (s[index-1].equals(s[pre])) {
                 next[index++] = ++pre;
             } else if (pre > 0) {
                 pre = next[pre];
             } else {
                 index++;
             }
         }
         return next;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(3);
        node1.left = new TreeNode(4);
        node1.right = new TreeNode(5);
        node1.left.left = new TreeNode(1);
        node1.left.right = new TreeNode(2);

        TreeNode node2 = new TreeNode(4);
        node2.left = new TreeNode(1);
        node2.right = new TreeNode(2);

        Leetcode572 leetcode572 = new Leetcode572();
        System.out.println(leetcode572.isSubtree2(node1, node2));

    }


}
