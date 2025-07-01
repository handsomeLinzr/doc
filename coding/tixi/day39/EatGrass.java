package coding.tixi.day39;

/**
 *
 * 给一个正整数 N，表示 N份青草统一放仓库里，
 * 有一只牛和一只羊，牛先吃，羊后吃，轮流吃，每一轮能吃的草必须是：
 * 4的n次方份草，包括 1
 * 当吃到对方没得吃，则胜利
 * 要求计算出牛还是羊赢
 *
 * @author linzherong
 * @date 2025/6/30 00:31
 */
public class EatGrass {

    public static String whoWin(int N) {
        if (N == 0) {
            // 牛没得吃，羊赢
            return "羊";
        }
        // 牛先吃，牛吃了 eat
        int eat = 1;
        while (eat <= N) {
            // 剩下的 N-eat 到羊吃
            if ("羊".equals(whoWin(N-eat))) {
                return "牛";
            }
            if (eat <= N/4) {
                // 避免 eat * 4 直接越界
                eat <<= 2;
            } else {
                break;
            }
        }
        return "羊";
    }

    /**
     * 对数器后得到的规律
     * @param N
     * @return
     */
    public static String whoWin1(int N) {
        N %= 5;
        return N == 0 || N == 2? "羊":"牛";
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            if (!whoWin(i).equals(whoWin1(i))) {
                System.out.println("Oops");
            }
        }
        System.out.println("结束");
    }

}
