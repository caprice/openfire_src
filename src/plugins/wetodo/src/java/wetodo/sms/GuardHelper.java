package wetodo.sms;

import java.util.HashMap;
import java.util.Map;

public class GuardHelper {
    private static final int MAX_COUNTS = 3;
    private static final long INTERVAL = 900000; // 15 min
    private static Map<String, Guard> guardMap = new HashMap<String, Guard>();

    public static boolean isOverload(String username) {
        Guard guard = guardMap.get(username);
        if (guard == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        if (now - guard.getTime() < INTERVAL && guard.getCount() >= MAX_COUNTS) {
            return true;
        }
        return false;
    }

    public static void fill(String username) {
        Guard guard = guardMap.get(username);
        long now = System.currentTimeMillis();
        if (guard == null || now - guard.getTime() > INTERVAL) {
            guardMap.put(username, new Guard(username, now, 1));
        } else {
            guard.setCount(guard.getCount() + 1);
            guardMap.put(username, guard);
        }
    }

}
