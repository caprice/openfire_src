package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

import java.text.SimpleDateFormat;

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskgroupElement.addAttribute("create_date", sdf.format(taskGroup.getCreate_date()));
        taskgroupElement.addAttribute("modify_date", sdf.format(taskGroup.getModify_date()));
        return lacoolElement;
    }
}
