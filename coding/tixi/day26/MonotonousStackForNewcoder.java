package coding.tixi.day26;

import java.io.*;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * https://www.nowcoder.com/practice/2a2c00e7a88a498693568cef63a4b7bb
 *
 * @author linzherong
 * @date 2025/6/15 19:28
 */
public class MonotonousStackForNewcoder {

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int N = (int) in.nval;
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            int[][] ans = process(arr);
            for (int[] result : ans) {
                out.println(result[0] + " " + result[1]);
            }
            out.flush();
        }
    }

    public static int[][] process(int[] arr) {
        int N = arr.length;
        int[][] ans = new int[N][2];
        Stack<LinkedList<Integer>> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && arr[stack.peek().peekLast()] > arr[i]) {
                LinkedList<Integer> cList = stack.pop();
                for (Integer pop : cList) {
                    ans[pop][0] = stack.isEmpty()?-1:stack.peek().peekLast();
                    ans[pop][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().getLast()] == arr[i]) {
                stack.peek().addLast(i);
            } else {
                LinkedList<Integer> cList = new LinkedList<>();
                cList.addLast(i);
                stack.push(cList);
            }
        }
        while (!stack.isEmpty()) {
            LinkedList<Integer> cList = stack.pop();
            for (Integer pop : cList) {
                ans[pop][0] = stack.isEmpty()?-1:stack.peek().peekLast();
                ans[pop][1] = -1;
            }
        }
        return ans;
    }

}
