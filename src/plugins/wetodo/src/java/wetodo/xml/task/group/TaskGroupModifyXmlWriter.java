package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

public class TaskGroupModifyXmlWriter {

    public static Element write(TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        Element taskgroupElement = lacoolElement.addElement("taskgroup");
        taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));
        taskgroupElement.addAttribute("name", taskGroup.getName());
        taskgroupElement.addAttribute("roomid", String.valueOf(taskGroup.getRoomid()));
        taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));
        taskgroupElement.addAttribute("create_date", taskGroup.getCreate_date().toString());
        taskgroupElement.addAttribute("modify_date", taskGroup.getModify_date().toString());
        return lacoolElement;
    }
}
