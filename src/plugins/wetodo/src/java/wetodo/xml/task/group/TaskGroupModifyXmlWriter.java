package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

public class TaskGroupModifyXmlWriter {

    public static Element write(String roomid, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        lacoolElement.addAttribute("roomid", roomid);

        Element taskgroupElement = lacoolElement.addElement("taskgroup", namespace);
        taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));
        taskgroupElement.addAttribute("name", taskGroup.getName());
        taskgroupElement.addAttribute("roomid", taskGroup.getRoomid());
        taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

        taskgroupElement.addAttribute("create_date", String.valueOf(taskGroup.getCreate_date().getTime()));
        taskgroupElement.addAttribute("modify_date", String.valueOf(taskGroup.getModify_date().getTime()));
        return lacoolElement;
    }
}
