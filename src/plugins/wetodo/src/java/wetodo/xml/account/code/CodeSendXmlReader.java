package wetodo.xml.account.code;

import org.dom4j.Element;

public class CodeSendXmlReader {

    public static String getPhone(Element lacoolElement) {
        Element phoneElement = lacoolElement.element("phone");
        return phoneElement.getStringValue();
    }

    public static String getCountryCode(Element lacoolElement) {
        Element countryCodeElement = lacoolElement.element("country_code");
        return countryCodeElement.getStringValue();
    }

}
