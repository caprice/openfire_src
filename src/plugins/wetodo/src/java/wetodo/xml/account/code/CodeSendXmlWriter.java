package wetodo.xml.account.code;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CodeSendXmlWriter {

    public static Element write(String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        return lacoolElement;
    }

}
