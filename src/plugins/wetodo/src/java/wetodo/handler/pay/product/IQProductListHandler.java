package wetodo.handler.pay.product;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.ProductManager;
import wetodo.model.Product;
import wetodo.xml.pay.product.ProductListXmlWriter;

import java.util.List;

public class IQProductListHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:member:query:product";
    private IQHandlerInfo info;
    private ProductManager productManager;

    public IQProductListHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
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
        List<Product> list = productManager.list();

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = ProductListXmlWriter.write(list, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        productManager = new ProductManager();
    }
}
