package wetodo.xml.account;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class AccountRegisterXmlWriter {

    public static Element write(String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        return lacoolElement;
    }

}
