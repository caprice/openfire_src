package wetodo.xml.room;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.User;

import java.util.List;

public class RoomMemberXmlWriter {

    public static Element write(List<User> list, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        for (User user : list) {
            Element itemElement = lacoolElement.addElement("item", namespace);
            itemElement.addAttribute("jid", user.getJID());
            itemElement.addAttribute("nickname", user.getName());
        }

        return lacoolElement;
    }

}
