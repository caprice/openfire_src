package wetodo.manager;

import wetodo.dao.TaskDAO;
import wetodo.model.Task;

import java.util.List;

public class TaskManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static TaskManager instance;

    public static TaskManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (TaskManager.class) {
                instance = new TaskManager();
            }
        }
        return instance;
    }

    public List<Task> list_all(int roomid) {
        return TaskDAO.list_all(roomid);
    }

    public List<Task> list(int roomid, String tgid) {
        return TaskDAO.list(roomid, tgid);
    }
}
