package wetodo.manager;

import wetodo.dao.TaskDAO;
import wetodo.dao.TaskGroupDAO;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> add(Task task) {
        task = TaskDAO.add(task);
        TaskGroupDAO.updateVersion(task.getTgid());
        TaskGroup taskGroup = TaskGroupDAO.find(task.getTgid());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("task", task);
        resultMap.put("taskgroup", taskGroup);
        return resultMap;
    }
}
