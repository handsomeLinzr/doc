package coding.tixi.day15;

import java.util.*;

/**
 * 最多会议问题
 *
 * @author linzherong
 * @date 2025/5/5 22:32
 */
public class BaseArrange {

    public static class Program {
        public int start;
        public int end;
        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int baseArrange(Program[] programs) {
        // 按 end 排序
        PriorityQueue<Program> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.end));
        queue.addAll(Arrays.asList(programs));
        int i = 0;
        while (!queue.isEmpty()) {
            i++;
            // 当前的会议
            Program poll = queue.poll();
            while (!queue.isEmpty() && queue.peek().start < poll.end) {
                // 移除掉start在当前会议end之前的会议
                queue.poll();
            }
        }
        return i;
    }

    // 暴力破解
    public static int baseArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process1(programs);
    }
    public static int process1(Program[] programs) {
        if (programs.length == 0) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < programs.length; i++) {
            Program[] nextProgram = removeIndex(programs, i);
            max = Math.max(max, process1(nextProgram));
        }
        return max+1;
    }
    // 移除不符合的数据，包括 index 位置的元素 和 start 在 index位置元素的 end 之前的元素
    public static Program[] removeIndex(Program[] programs, int index) {
        List<Program> list = new ArrayList<>();
        Program rp = programs[index];
        for (int i = 0; i < programs.length; i++) {
            if (i != index && programs[i].start >= rp.end) {
                list.add(programs[i]);
            }
        }
        return list.toArray(new Program[0]);
    }

    public static Program[] general(int maxLength, int maxValue) {
        Program[] programs = new Program[(int)(Math.random() * maxLength)];
        for (int i = 0; i < programs.length; i++) {
            int start = (int) (Math.random() * maxValue);
            int end = (int) (Math.random() * maxValue);
            if (start == end) {
                programs[i] = new Program(start, start+1);
            } else {
                programs[i] = new Program(Math.min(start, end), Math.max(start, end));
            }
        }
        return programs;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            Program[] programs = general(10, 25);
            if (baseArrange1(programs) != baseArrange(programs)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
