package wetodo.xml.pay;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class PayPurchaseXmlWriter {

    public static Element write(String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        return lacoolElement;
    }

}

