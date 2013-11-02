package wetodo.sms;

import wetodo.exception.AuthCodeOverloadException;

import java.util.HashMap;
import java.util.Map;

public class Code {

    private static final long CODE_MIN = 100000;
    private static final long CODE_MAX = 999999;
    private static Map<String, Long> codeMap = new HashMap<String, Long>();

    private static long make() {
        return Math.round(Math.random() * (CODE_MAX - CODE_MIN) + CODE_MIN);
    }

    public static long get(String username) throws AuthCodeOverloadException {
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



