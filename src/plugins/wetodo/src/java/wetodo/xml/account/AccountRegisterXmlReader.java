package wetodo.xml.account;

import org.dom4j.Element;

public class AccountRegisterXmlReader {

    public static String getPhone(Element lacoolElement) {
        Element phoneElement = lacoolElement.element("phone");
        return phoneElement.getStringValue();
    }

    public static String getCountryCode(Element lacoolElement) {
        Element countryCodeElement = lacoolElement.element("country_code");
        return countryCodeElement.getStringValue();
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
