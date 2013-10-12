package wetodo.handler.pay;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.PayManager;
import wetodo.xml.pay.PayPurchaseXmlReader;
import wetodo.xml.pay.PayPurchaseXmlWriter;

public class IQPayPurchaseHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:member:verify:product";
    private IQHandlerInfo info;
    private PayManager payManager;

    public IQPayPurchaseHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:member:verify:product===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        int productId = PayPurchaseXmlReader.getProductId(lacoolElement);
        String receipt = PayPurchaseXmlReader.getReceipt(lacoolElement);

        // persistent to db
        payManager.purchase();

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = PayPurchaseXmlWriter.write(NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        payManager = new PayManager();
    }
}
