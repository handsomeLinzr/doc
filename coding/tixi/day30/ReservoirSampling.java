package coding.tixi.day30;

/**
 *
 * 蓄水池问题
 *
 * @author linzherong
 * @date 2025/6/28 19:49
 */
public class ReservoirSampling {

    public static class RandomBox {
        int capacity;  // 容量
        int[] bag;   // 包
        int count;   // 总量
        public RandomBox(int capacity) {
            this.capacity = capacity;
            bag = new int[capacity];
            count = 0;
        }

        // 增加
        public void add(int num) {
            if (count++ < capacity) {
                bag[count-1] = num;
            } else if (random(num) <= 10) {
                bag[(int)(Math.random()*10)] = num;
            }
        }

        // 结果
        public int[] choices() {
            int[] choice = new int[10];
            for (int i = 0; i < 10; i++) {
                choice[i] = bag[i];
            }
            return choice;
        }

        // 随机返回 1 到 num
        public int random(int num) {
            return (int) (Math.random() * num) + 1;
        }

    }


    /**
     * 随机返回 1 到 index
     * @param index
     * @return
     */
    public static int getRandom(int index) {
        return (int) (Math.random() * index) + 1;
    }


    public static void main(String[] args) {
        System.out.println("开始");
        int testTime = 10000;

        int ballNum = 17;
        int[] count = new int[ballNum+1];
        for (int time = 0; time < testTime; time++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= ballNum ; num++) {
                if (num <= 10) {
                    bag[bagi++] = num;
                } else {
                    if (getRandom(num) <= 10) {
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = num;
                    }
                }
            }
            // 统计
            for (int i : bag) {
                count[i]++;
            }
        }
        for (int i = 0; i < count.length; i++) {
            System.out.println(count[i]);
        }

        System.out.println("==============================");

        int all = 100;
        int bag = 10;
        int[] count2 = new int[101];
        for (int time = 0; time < 50000; time++) {
            RandomBox box = new RandomBox(bag);
            for (int i = 1; i <= all; i++) {
                box.add(i);
            }
            for (int choice : box.choices()) {
                count2[choice]++;
            }
        }

        for (int i = 0; i < count2.length; i++) {
            System.out.println(count2[i]);
        }

        System.out.println("结束");
    }

}
