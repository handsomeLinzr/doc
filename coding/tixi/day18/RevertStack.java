package coding.tixi.day18;

import java.util.Stack;

/**
 *
 * 将栈的逆序
 *
 * @author linzherong
 * @date 2025/5/25 19:41
 */
public class RevertStack {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        revert(stack);
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

    // 不额外申请空间，利用系统栈，将原有数据逆序
    // 思考过程：1.栈只有pop和push，而要每次都减少，必须每次都pop  2.通过pop想办法获取到栈低元素，放到最上边
    public static void revert(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int last = getLast(stack);
        revert(stack);
        stack.push(last);
    }

    // 获取栈低数据
    public static int getLast(Stack<Integer> stack) {
        Integer pop = stack.pop();
        if (stack.isEmpty()) {
            return pop;
        }
        int last = getLast(stack);
        stack.push(pop);
        return last;
    }


}
