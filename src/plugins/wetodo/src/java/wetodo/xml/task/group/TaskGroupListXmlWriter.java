package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

import java.util.List;

public class TaskGroupListXmlWriter {

    public static Element write(List<TaskGroup> list, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        for (TaskGroup taskGroup : list) {
            Element taskgroupElement = lacoolElement.addElement("taskgroup");
            taskgroupElement.addAttribute("tgid", taskGroup.getTgid());
            taskgroupElement.addAttribute("name", taskGroup.getName());
            taskgroupElement.addAttribute("roomid", String.valueOf(taskGroup.getRoomid()));
            taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));
            taskgroupElement.addAttribute("create_date", taskGroup.getCreate_date().toString());
            taskgroupElement.addAttribute("modify_date", taskGroup.getModify_date().toString());
        }

        return lacoolElement;
    }
}
