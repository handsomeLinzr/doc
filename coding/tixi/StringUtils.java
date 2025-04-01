package coding.tixi;

/**
 * @author linzherong
 * @date 2025/4/1 22:45
 */
public class StringUtils {

    public static String generalStr(int min, int max) {
        int length = (int) (Math.random() * (max-min)) + min;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charNum = (int) (Math.random() * 26) + 97;
            str.append((char) charNum);
        }
        return str.toString();
    }

    public static boolean isBlack(String str) {
        return str == null || str.equals("");
    }

    public static void main(String[] args) {
        System.out.println(generalStr(6,7));
    }

}
