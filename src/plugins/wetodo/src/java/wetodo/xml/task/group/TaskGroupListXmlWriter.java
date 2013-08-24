package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

import java.text.SimpleDateFormat;
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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            taskgroupElement.addAttribute("create_date", sdf.format(taskGroup.getCreate_date()));
            taskgroupElement.addAttribute("modify_date", sdf.format(taskGroup.getModify_date()));
        }

        return lacoolElement;
    }
}
