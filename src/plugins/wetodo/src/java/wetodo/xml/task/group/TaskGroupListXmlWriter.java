package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

import java.util.List;

public class TaskGroupListXmlWriter {

    public static Element write(String roomid, List<TaskGroup> list, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        lacoolElement.addAttribute("roomid", roomid);

        for (TaskGroup taskGroup : list) {
            Element taskgroupElement = lacoolElement.addElement("taskgroup", namespace);
            taskgroupElement.addAttribute("tgid", taskGroup.getTgid());
            taskgroupElement.addAttribute("name", taskGroup.getName());
            taskgroupElement.addAttribute("roomid", String.valueOf(taskGroup.getRoomid()));
            taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

            taskgroupElement.addAttribute("create_date", String.valueOf(taskGroup.getCreate_date().getTime()));
            taskgroupElement.addAttribute("modify_date", String.valueOf(taskGroup.getModify_date().getTime()));
        }

        return lacoolElement;
    }
}
