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
    public static Element write(List<Task> list, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

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

            Element taskGroupElement = lacoolElement.addElement("taskgroup");
            taskGroupElement.addAttribute("tgid", taskGroup.getTgid());
            taskGroupElement.addAttribute("roomid", String.valueOf(taskGroup.getRoomid()));
            taskGroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));
            taskGroupElement.addAttribute("create_date", taskGroup.getCreate_date().toString());
            taskGroupElement.addAttribute("modify_date", taskGroup.getModify_date().toString());

            for (Task task : taskList) {
                Element taskElement = taskGroupElement.addElement("task");
                taskElement.addAttribute("tid", task.getTid());
                taskElement.addAttribute("roomid", String.valueOf(task.getRoomid()));
                taskElement.addAttribute("name", task.getName());
                taskElement.addAttribute("status", String.valueOf(task.getStatus()));
                taskElement.addAttribute("create_date", taskGroup.getCreate_date().toString());
                taskElement.addAttribute("modify_date", taskGroup.getModify_date().toString());
            }

        }

        return lacoolElement;
    }

}
