package wetodo.xml.pay;

import org.dom4j.Element;

public class PayPurchaseXmlReader {

    public static String getIapId(Element lacoolElement) {
        Element productElement = lacoolElement.element("product");
        return productElement.attribute("id").getValue();
    }

    public static String getReceipt(Element lacoolElement) {
        Element productElement = lacoolElement.element("product");
        return productElement.attribute("receipt_data").getValue();
    }
}
