package coding.tixi.day18;

/**
 *
 * 圆盘转移游戏步骤
 * 打印n层汉诺塔从左边移动到右边的全部过程
 *
 * @author linzherong
 * @date 2025/5/25 18:25
 */
public class Hanoi {

    public static void hanoi1(int n) {
        if (n == 0) {
            return;
        }
        leftToRight(n);
    }

    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to right");
            return;
        }
        leftToMiddle(n-1);
        System.out.println("move "+ n +" from left to right");
        middleToRight(n-1);
    }
    public static void leftToMiddle(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to middle");
            return;
        }
        leftToRight(n-1);
        System.out.println("move "+ n +" from left to middle");
        rightToMiddle(n-1);
    }
    public static void rightToMiddle(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to middle");
            return;
        }
        rightToLeft(n-1);
        System.out.println("move "+ n +" from right to middle");
        leftToMiddle(n-1);
    }
    public static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to left");
            return;
        }
        rightToMiddle(n-1);
        System.out.println("move "+ n +" from right to left");
        middleToLeft(n-1);
    }
    public static void middleToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from middle to left");
            return;
        }
        middleToRight(n-1);
        System.out.println("move "+n+" from middle to left");
        rightToLeft(n-1);
    }
    public static void middleToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from middle to right");
            return;
        }
        middleToLeft(n-1);
        System.out.println("move "+ n +" from middle to right");
        leftToRight(n-1);
    }

    public static void func(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("move 1 from "+ from +" to " + to);
            return;
        }
        func(n-1, from, other, to);
        System.out.println("move "+ n +" from "+ from +" to " + to);
        func(n-1, other, to, from);
    }

    public static void hanoi2(int n) {
        if (n == 0) {
            return;
        }
        func(n, "left", "right", "middle");
    }

    public static int needStep(int n) {
        if ( n == 1) {
            return 1;
        }
        return needStep(n - 1) + 1 + needStep(n-1);
    }

    public static void main(String[] args) {
        hanoi1(5);
        System.out.println("==========================");
        hanoi2(5);
        System.out.println("==========================");
        System.out.println(needStep(5));
    }

}
