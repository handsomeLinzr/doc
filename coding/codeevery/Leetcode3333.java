package coding.codeevery;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * 1 <= word.length <= 5 * 105
 * word 只包含小写英文字母。
 * 1 <= k <= 2000
 *
 * @author linzherong
 * @date 2025/7/2 16:37
 */
public class Leetcode3333 {

    public int possibleStringCount(String word, int k) {
        // 字符词频
        List<Integer> fre = new ArrayList<>(26);
        int count = 1;
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) == word.charAt(i-1)) {
                count++;
            } else {
                fre.add(count);
                count = 1;
            }
        }
        fre.add(count);
        // 总数量
        long total = 1;
        for (int i = 0; i < fre.size(); i++) {
            total *= fre.get(i);
        }
        if (fre.size() >= k) {
            // 词频种类超过k，直接返回所有
            return (int) total;
        }
        // 先给每个位置占一个位置
        k = k-fre.size();
        for (int i = 0; i < fre.size(); i++) {
            fre.set(i, fre.get(i)-1);
        }
        // 接下来只要获取fre中每个组组合成小于k的数量的组合数即可
        long process = process(fre, 0, k - 1);
        return (int) ((total - process) % 1000000007);
    }

    public long process(List<Integer> fre, int index, int k) {
        if (k < 0) {
            return 0;
        }
        if (index == fre.size()) {
            return 1;
        }
        long result = 0;
        for (int i = 0; i <= fre.get(index); i++) {
            result += process(fre, index+1, k-i);
        }
        return result;
    }

    private static final int mod = 1000000007;

    public int possibleStringCount1(String word, int k) {
        // 字符词频
        List<Integer> fre = new ArrayList<>(26);
        int count = 1;
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) == word.charAt(i-1)) {
                count++;
            } else {
                fre.add(count);
                count = 1;
            }
        }
        fre.add(count);
        // 总数量
        long total = 1;
        for (int i = 0; i < fre.size(); i++) {
            total = total * fre.get(i) % mod;
        }
        if (fre.size() >= k) {
            // 词频种类超过k，直接返回所有
            return (int) total;
        }
        // 先给每个位置占一个位置
        k = k-fre.size();
        for (int i = 0; i < fre.size(); i++) {
            fre.set(i, fre.get(i)-1);
        }
        // 接下来只要获取fre中每个组组合成小于k的数量的组合数即可
        int N = fre.size();
        long [][] dp = new long[N+1][k];
        for (int j = 0; j < k; j++) {
            dp[N][j] = 1;
        }
        for (int i = 0; i < fre.size(); i++) {
            dp[i][0] = 1;
        }
        for (int i = N-1; i >= 0 ; i--) {
            for (int j = 1; j < k; j++) {
                dp[i][j] = (dp[i][j-1] + dp[i+1][j]) % mod;
                if (j >= fre.get(i)+1) {
                    dp[i][j] = (dp[i][j] - dp[i+1][j-1-fre.get(i)] + mod) % mod;
                }
            }
        }
        return (int) ((total - dp[0][k-1] + mod) % mod);
    }


    public static void main(String[] args) {
        Leetcode3333 leetcode3333 = new Leetcode3333();
//        System.out.println(leetcode3333.possibleStringCount("aaafffffeeeeoooooobbbbbbbbggghhhhhhheeeeeeewwwwwwwwiiiiiiiijjjjjjjjaaaannnnnnniiiiiiimmmmmmmmrrrrrrrriieeeiiiiiiyyyybbbbbbbbbbbbbbbbbbbbzzzzeelllllllliiippppmmmmmmmmmmuuuuuuunnnnnnnnzzzzzzzzzzijjjjvvb", 118));
        System.out.println(leetcode3333.possibleStringCount1("aaafffffeeeeoooooobbbbbbbbggghhhhhhheeeeeeewwwwwwwwiiiiiiiijjjjjjjjaaaannnnnnniiiiiiimmmmmmmmrrrrrrrriieeeiiiiiiyyyybbbbbbbbbbbbbbbbbbbbzzzzeelllllllliiippppmmmmmmmmmmuuuuuuunnnnnnnnzzzzzzzzzzijjjjvvb", 118));
    }


}
