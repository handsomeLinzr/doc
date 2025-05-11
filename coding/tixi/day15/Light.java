package coding.tixi.day15;

/**
 * 字符串由 X 和 . 组成，
 *  X 不可以放灯，可以不亮， .必须亮，
 *  等可以照亮 i-1,i,i+1 三个位置
 *  要求返回所有 . 都照亮的最小灯数量
 * @author linzherong
 * @date 2025/5/6 00:44
 */
public class Light {

    public static int minLight(String lightStr) {
        int light = 0;
        for (int i = 0; i < lightStr.length(); i++) {
            char c = lightStr.charAt(i);
            if (c == '.') {
                light++;
                if (lightStr.length() == i+1) {
                    return light;
                }
                if (lightStr.charAt(i+1) == 'X') {
                    i+=1;
                } else {
                    i+=2;
                }
            }
        }
        return light;
    }

    // 暴力破解
    public static int minLight1(String lightStr) {

        return process1(lightStr);
    }
    public static int process1(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) == '.') {
                break;
            }
            i++;
        }
        if (i == str.length()) {
            return 0;
        }
        if (i+1 == str.length()) {
            return 1;
        }
        if (str.charAt(i+1) == 'X') {
            return 1 + process1(subString(str,i+2));
        } else {
            return 1 + process1(subString(str, i+3));
        }
    }
    public static String subString(String str, int beginIndex) {
        if (beginIndex >= str.length()) {
            return null;
        }
        return str.substring(beginIndex);
    }

    public static int minLight2(String lightStr) {
        int light = 0;
        double point = 0;
        for (int i = 0; i < lightStr.length(); i++) {
            if (lightStr.charAt(i) == 'X') {
                light += (int) (Math.ceil(point/3));
                point = 0;
            } else {
                point++;
            }
        }
        return point > 0? light + (int) Math.ceil(point/3):light;
    }

    public static String generalLightStr(int maxLength) {
        int length = (int) (Math.random() * maxLength);
        String ans = "";
        for (int i = 0; i < length; i++) {
            ans += (Math.random() < 0.3? "X" : ".");
        }
        return ans;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            String s = generalLightStr(15);
            if (minLight(s) != minLight2(s) || minLight1(s) != minLight2(s)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
