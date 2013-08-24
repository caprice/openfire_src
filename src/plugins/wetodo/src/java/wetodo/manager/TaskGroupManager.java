package wetodo.manager;

import wetodo.dao.TaskGroupDAO;
import wetodo.model.TaskGroup;

import java.util.List;

public class TaskGroupManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static TaskGroupManager instance;

    public static TaskGroupManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (TaskGroupManager.class) {
                instance = new TaskGroupManager();
            }
        }
        return instance;
    }

    public TaskGroup add(TaskGroup taskGroup) {
        return TaskGroupDAO.add(taskGroup);
    }

    public List<TaskGroup> list(String roomid) {
        return TaskGroupDAO.list(roomid);
    }

    public TaskGroup find(String tgid) {
        return TaskGroupDAO.find(tgid);
    }

    public boolean modify(TaskGroup taskGroup) {
        return TaskGroupDAO.modify(taskGroup);
    }

    public boolean del(String tgid) {
        return TaskGroupDAO.del(tgid);
    }
}
