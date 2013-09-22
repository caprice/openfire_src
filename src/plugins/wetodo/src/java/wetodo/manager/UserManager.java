package wetodo.manager;

import wetodo.dao.UserDAO;
import wetodo.model.User;

public class UserManager {

    private static UserManager instance;

    /**
     * 单例产生实例
     *
     * @return
     */
    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                instance = new UserManager();
            }
        }
        return instance;
    }

    public User findByUsername(String username) {
        return UserDAO.findByUsername(username);
    }
}
