package wetodo.handler.pay.product;

import org.jivesoftware.openfire.IQHandlerInfo;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.handler.IQBaseHandler;
import wetodo.manager.ProductManager;
import wetodo.model.Product;
import wetodo.xml.pay.product.ProductListXmlWriter;

import java.util.List;

public class IQProductListHandler extends IQBaseHandler {
    protected String namespace = "lacool:member:query:product";

    public IQProductListHandler() {
        super();
        this.info = new IQHandlerInfo("lacool", namespace);
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:member:query:product===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);

            return result;
        }

        // xml reader

        // persistent to db
        List<Product> list = ProductManager.getInstance().list();

        // output
        return result(packet, ProductListXmlWriter.write(list, namespace));
    }

}
