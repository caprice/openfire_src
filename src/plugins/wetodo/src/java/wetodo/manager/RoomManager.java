package wetodo.manager;

import wetodo.dao.RoomDAO;
import wetodo.model.Room;

import java.util.List;

public class RoomManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static RoomManager instance;

    public static RoomManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (RoomManager.class) {
                instance = new RoomManager();
            }
        }
        return instance;
    }

    public static List<Room> list(String username) {
        return RoomDAO.list(username);
    }

}
