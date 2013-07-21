package wetodo.xml.account;

import org.dom4j.Element;

public class AccountRegisterXmlReader {

    public static String getPhone(Element lacoolElement) {
        Element phoneNumberElement = lacoolElement.element("phone_number");
        return phoneNumberElement.getStringValue();
    }

    public static String getAuthCode(Element lacoolElement) {
        Element authCodeElement = lacoolElement.element("auth_code");
        return authCodeElement.getStringValue();
    }

    public static String getUsername(Element lacoolElement) {
        Element usernameElement = lacoolElement.element("username");
        return usernameElement.getStringValue();
    }

    public static String getNickname(Element lacoolElement) {
        Element nicknameElement = lacoolElement.element("nickname");
        return nicknameElement.getStringValue();
    }

    public static String getPassword(Element lacoolElement) {
        Element passwordElement = lacoolElement.element("password");
        return passwordElement.getStringValue();
    }


}
