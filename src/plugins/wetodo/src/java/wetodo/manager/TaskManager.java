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

    public List<Task> list_all(String roomid) {
        return TaskDAO.list_all(roomid);
    }

    public List<Task> list(String roomid, String tgid) {
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

    public Map<String, Object> modify(Task task) {
        TaskDAO.modify(task);
        task = TaskDAO.find(task.getTid());
        TaskGroupDAO.updateVersion(task.getTgid());
        TaskGroup taskGroup = TaskGroupDAO.find(task.getTgid());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("task", task);
        resultMap.put("taskgroup", taskGroup);
        return resultMap;
    }

    public Map<String, Object> del(Task task) {
        TaskDAO.del(task.getTid());
        TaskGroupDAO.updateVersion(task.getTgid());
        TaskGroup taskGroup = TaskGroupDAO.find(task.getTgid());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("taskgroup", taskGroup);
        return resultMap;
    }
}
