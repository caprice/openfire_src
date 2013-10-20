package wetodo.handler.pay;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.error.IQError;
import wetodo.exception.ReceiptAlreadyExistsException;
import wetodo.exception.ReceiptIAPValidFailException;
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

        IQ reply;

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String iapId = PayPurchaseXmlReader.getIapId(lacoolElement);
        String receipt = PayPurchaseXmlReader.getReceipt(lacoolElement);
        String username = packet.getFrom().getNode();

        // persistent to db
        try {
            payManager.purchase(username, receipt, iapId);
        } catch (ReceiptAlreadyExistsException e) {
            reply = IQ.createResultIQ(packet);
            reply.setType(IQ.Type.error);
            reply.setChildElement(IQError.getError(packet.getChildElement().createCopy(), IQError.Condition.receipt_exist));

            return reply;
        } catch (ReceiptIAPValidFailException e) {
            reply = IQ.createResultIQ(packet);
            reply.setType(IQ.Type.error);
            reply.setChildElement(IQError.getError(packet.getChildElement().createCopy(), IQError.Condition.receipt_iap_valid_fail));

            return reply;
        }

        // output
        reply = IQ.createResultIQ(packet);
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
