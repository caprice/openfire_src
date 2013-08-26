package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.manager.TaskGroupManager;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListAllXmlWriter {
    public static Element write(String roomid, List<Task> list, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        lacoolElement.addAttribute("roomid", roomid);

        Map<String, TaskGroup> mapTaskGroup = new HashMap<String, TaskGroup>();
        Map<String, List<Task>> mapTaskList = new HashMap<String, List<Task>>();

        for (Task task : list) {
            String tgid = task.getTgid();
            if (mapTaskList.containsKey(tgid)) {
                List<Task> taskList = mapTaskList.get(tgid);
                taskList.add(task);
            } else {
                List<Task> taskList = new ArrayList<Task>();
                taskList.add(task);
                mapTaskList.put(tgid, taskList);

                TaskGroup taskGroup = TaskGroupManager.getInstance().find(tgid);
                mapTaskGroup.put(tgid, taskGroup);
            }
        }

        for (Map.Entry<String, List<Task>> entry : mapTaskList.entrySet()) {
            String tgid = entry.getKey();
            List<Task> taskList = entry.getValue();

            TaskGroup taskGroup = mapTaskGroup.get(tgid);

            Element taskGroupElement = lacoolElement.addElement("taskgroup", namespace);
            taskGroupElement.addAttribute("tgid", taskGroup.getTgid());
            taskGroupElement.addAttribute("roomid", taskGroup.getRoomid());
            taskGroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

            taskGroupElement.addAttribute("create_date", String.valueOf(taskGroup.getCreate_date().getTime()));
            taskGroupElement.addAttribute("modify_date", String.valueOf(taskGroup.getModify_date().getTime()));

            for (Task task : taskList) {
                Element taskElement = taskGroupElement.addElement("task", namespace);
                taskElement.addAttribute("tid", task.getTid());
                taskElement.addAttribute("roomid", task.getRoomid());
                taskElement.addAttribute("name", task.getName());
                taskElement.addAttribute("status", String.valueOf(task.getStatus()));

                taskElement.addAttribute("create_date", String.valueOf(task.getCreate_date().getTime()));
                taskElement.addAttribute("modify_date", String.valueOf(task.getModify_date().getTime()));
            }

        }

        return lacoolElement;
    }

}
