package wetodo.xml.account;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.User;

public class AccountInfoXmlWriter {

    public static Element write(String namespace, User user) {
        Element lacoolElement = DocumentHelper.createElement("lacool");

        Element memberElement = lacoolElement.addElement("member", namespace);
        memberElement.addAttribute("left", String.valueOf(user.getVip_expire()));

        return lacoolElement;
    }

}
