package wetodo.manager;

public class CodeManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static CodeManager instance;

    public static CodeManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (CodeManager.class) {
                instance = new CodeManager();
            }
        }
        return instance;
    }

    public static boolean send(String phone) {
        return true;
    }

    public static boolean validate(String phone, String authCode) {
        return true;
    }

}
