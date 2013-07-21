package wetodo.xml.account.code;

import org.dom4j.Element;

public class CodeValidateXmlReader {

    public static String getPhone(Element lacoolElement) {
        Element phoneNumberElement = lacoolElement.element("phone_number");
        return phoneNumberElement.getStringValue();
    }

    public static String getAuthCode(Element lacoolElement) {
        Element authCodeElement = lacoolElement.element("auth_code");
        return authCodeElement.getStringValue();
    }

}
