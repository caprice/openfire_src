package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

public class TaskGroupDelXmlWriter {

    public static Element write(TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        Element taskgroupElement = lacoolElement.addElement("taskgroup");
        taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));
        return lacoolElement;
    }
}
