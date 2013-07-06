package wetodo.manager;

import wetodo.dao.TaskGroupDAO;

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

    public void add(int roomID, String taskGroupName) {
        TaskGroupDAO.add(roomID, taskGroupName);
    }
}
