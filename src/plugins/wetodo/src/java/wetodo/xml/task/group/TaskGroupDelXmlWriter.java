package wetodo.xml.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.TaskGroup;

public class TaskGroupDelXmlWriter {

    public static Element write(String roomid, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = null;
        try {
            lacoolElement = DocumentHelper.createElement("lacool");
            lacoolElement.addNamespace("", namespace);
            lacoolElement.addAttribute("roomid", roomid);

            Element taskgroupElement = lacoolElement.addElement("taskgroup", namespace);
            taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));

            System.out.println(lacoolElement.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return lacoolElement;
    }
}
