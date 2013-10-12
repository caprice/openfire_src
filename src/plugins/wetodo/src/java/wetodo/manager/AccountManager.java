package wetodo.manager;

import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.jivesoftware.openfire.user.UserManager;
import wetodo.dao.UserDAO;

public class AccountManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static AccountManager instance;

    public static AccountManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (AccountManager.class) {
                instance = new AccountManager();
            }
        }
        return instance;
    }

    public static boolean register(String username, String password, String nickname, String phone, String authCode) throws UserAlreadyExistsException {
        UserManager userManager = UserManager.getInstance();
        User newUser = userManager.createUser(username, password, nickname, null);
        return true;
    }

    public static wetodo.model.User getUserInfo(String username) {
        return UserDAO.findByUsername(username);
    }


}
