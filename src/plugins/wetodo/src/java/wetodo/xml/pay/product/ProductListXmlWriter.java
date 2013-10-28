package wetodo.xml.pay.product;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Product;

import java.util.List;

public class ProductListXmlWriter {

    public static Element write(List<Product> list, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        for (Product product : list) {
            Element productElement = lacoolElement.addElement("product", namespace);
            productElement.addAttribute("id", product.getIap_id());
        }

        return lacoolElement;
    }

}
