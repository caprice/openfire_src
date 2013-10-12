package wetodo.xml.pay;

import org.dom4j.Element;

public class PayPurchaseXmlReader {

    public static int getProductId(Element lacoolElement) {
        Element productElement = lacoolElement.element("product");
        return Integer.parseInt(productElement.attribute("id").getValue());
    }

    public static String getReceipt(Element lacoolElement) {
        Element productElement = lacoolElement.element("product");
        return productElement.attribute("receipt_data").getValue();
    }
}
