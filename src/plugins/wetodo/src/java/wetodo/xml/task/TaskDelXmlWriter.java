package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

public class TaskDelXmlWriter {
    public static Element write(Task task, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        Element taskgroupElement = lacoolElement.addElement("taskgroup");
        taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));
        taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

        Element taskElement = taskgroupElement.addElement("task");
        taskElement.addAttribute("tid", task.getTid());
        return lacoolElement;
    }
}
