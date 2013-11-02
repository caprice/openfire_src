package wetodo.xml.account;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.User;

public class AccountInfoXmlWriter {

    public static Element write(String namespace, User user) {
        Element lacoolElement = DocumentHelper.createElement("lacool");

        Element memberElement = lacoolElement.addElement("member", namespace);
        long now = System.currentTimeMillis();
        long left = 0;
        if (user.getVip_expire().getTime() > now) {
            left = user.getVip_expire().getTime() - now;
        }
        memberElement.addAttribute("left", String.valueOf(left));

        return lacoolElement;
    }

}
