package wetodo.sms;

import java.util.HashMap;
import java.util.Map;

public class Code {

    private static Map<String, Long> codeMap = new HashMap<String, Long>();

    private static long make() {
        long min = 100000;
        long max = 999999;
        return Math.round(Math.random() * (max - min) + min);
    }

    public static long get(String username) {
        long code = make();
        codeMap.put(username, code);
        return code;
    }

    public static boolean validate(String username, long code) {
        if (!codeMap.containsKey(username)) {
            return false;
        }
        long codeRight = codeMap.get(username);
        return codeRight == code;
    }
}
