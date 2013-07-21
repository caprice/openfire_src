package wetodo.xml.account.code;

import org.dom4j.Element;

public class CodeSendXmlReader {

    public static String getPhone(Element lacoolElement) {
        Element phoneNumberElement = lacoolElement.element("phone_number");
        return phoneNumberElement.getStringValue();
    }

}
